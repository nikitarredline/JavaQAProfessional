package components;

import annotations.Component;
import commons.AbsCommon;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public abstract class AbsComponent extends AbsCommon {

    {
        assertThat(waiter.waitForCondition(ExpectedConditions.visibilityOfElementLocated(getByComponent())))
                .as("Error")
                .isTrue();
    }

    private By getByComponent() {
        Class clazz = getClass();
        if (clazz.isAnnotationPresent(Component.class)) {
            Component component = (Component) clazz.getDeclaredAnnotation(Component.class);
            String[] componentVal = component.value().split(":");
            switch (componentVal[0]) {
                case "css":
                    return By.cssSelector(componentVal[1]);
            }
        }

        return null;
    }

    public WebElement getComponentEntry() {
        return $(getByComponent());
    }
}
