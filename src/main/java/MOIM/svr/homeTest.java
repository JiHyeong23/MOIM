package MOIM.svr;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/home")
@AllArgsConstructor
public class homeTest {

    @GetMapping
    public ModelAndView access() {
        ModelAndView modelAndView = new ModelAndView("home");
        return modelAndView;
    }
}
