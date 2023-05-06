package jinookk.ourlms.infrastructure;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jinookk.ourlms.daos.CourseDao;
import jinookk.ourlms.dtos.CourseFilterDto;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.QCourse;
import jinookk.ourlms.models.enums.CourseStatus;
import jinookk.ourlms.models.enums.Level;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.HashTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
public class CourseDaoImpl implements CourseDao {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Page<Course> findCoursesByFilter(CourseFilterDto courseFilterDto, Pageable pageable) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QCourse course = QCourse.course;
        BooleanBuilder builder = new BooleanBuilder(course.status.eq(CourseStatus.APPROVED));

        if (courseFilterDto.level() != null) {
            builder.or(course.level.eq(Level.of(courseFilterDto.level())));
        }

        if (courseFilterDto.cost() != null) {
            BooleanExpression costExpression = courseFilterDto.cost().equals("무료")
                    ? course.price.value.eq(0)
                    : course.price.value.gt(0);

            builder.or(costExpression);
        }

        if (courseFilterDto.skill() != null) {
            builder.or(course.skillSets.contains(new HashTag(courseFilterDto.skill())));
        }

        if (courseFilterDto.content() != null) {
            builder.and(course.title.value.like("%" + courseFilterDto.content() + "%"))
                    .or(course.description.value.like("%" + courseFilterDto.content() + "%"))
                    .or(course.goals.contains(new Content("%" + courseFilterDto.content() + "%")));
        }

        JPAQuery<Course> courseJPAQuery = queryFactory.selectFrom(course)
                .where(builder);

        long totalCount = courseJPAQuery
                .fetch()
                .size();

        List<Course> courses = courseJPAQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(courses, pageable, totalCount);
    }

    @Override
    public Page<Course> findCoursesForAdmin(Pageable pageable) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QCourse course = QCourse.course;

        JPAQuery<Course> courseJPAQuery = queryFactory.selectFrom(course)
                .where(course.status.ne(CourseStatus.DELETED));

        long totalCount = courseJPAQuery
                .fetch()
                .size();

        List<Course> courses = courseJPAQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(course.createdAt.desc())
                .fetch();

        return new PageImpl<>(courses, pageable, totalCount);
    }
}
