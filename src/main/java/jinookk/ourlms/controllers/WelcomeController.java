package jinookk.ourlms.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jinookk.ourlms.dtos.SectionDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WelcomeController {
    @GetMapping
    @ApiOperation(value = "Greeting", notes = "This API Greeting.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SectionDto.class),
            @ApiResponse(code = 401, message = "Success", response = SectionDto.class),
            @ApiResponse(code = 403, message = "Success", response = SectionDto.class),
            @ApiResponse(code = 404, message = "Success", response = SectionDto.class),
    })
    public String greeting() {
        return "Hello, world!";
    }
}
