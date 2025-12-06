//package components;
//
//import annotations.Component;
//import com.google.inject.Inject;
//import commons.AbsCommon;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import support.GuiceScoped;
//import waiters.Waiter;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//public abstract class AbsComponent extends AbsCommon {
//
//
//
//    {
//        assertThat(waiter.waitForCondition(ExpectedConditions.visibilityOfElementLocated(getByComponent())))
//                .as("Error")
//                .isTrue();
//    }
//
//    @Inject
//    public AbsComponent(GuiceScoped guiceScoped, Waiter waiter) {
//        super(guiceScoped, waiter);
//    }
//
//    private By getByComponent() {
//        Class clazz = getClass();
//        if (clazz.isAnnotationPresent(Component.class)) {
//            Component component = (Component) clazz.getDeclaredAnnotation(Component.class);
//            String[] componentVal = component.value().split(":");
//            switch (componentVal[0]) {
//                case "css":
//                    return By.cssSelector(componentVal[1]);
//            }
//        }
//
//        return null;
//    }
//
//    public WebElement getComponentEntry() {
//        return $(getByComponent());
//    }
//}
