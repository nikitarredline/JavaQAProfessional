package components;

import annotations.Component;
import org.openqa.selenium.WebDriver;

@Component("css:.daynews__main")
public class DayNewsComponent extends AbsComponent {

    public DayNewsComponent(WebDriver driver) {
        super(driver);
    }
}
