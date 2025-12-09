//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//
//import java.math.BigDecimal;
//import java.util.*;
//import java.util.concurrent.*;
//import java.util.stream.Collectors;
//
//public class CoursesPageParallel {
//
////    @FindBy(css = "section a[href^='/online/'] h6 div")
////    private List<WebElement> onlineCourses;
//
////    private BigDecimal getCoursePrice(WebElement onlineCourses) {
////        clickCourseByTitle(onlineCourses.getText());
////        BigDecimal price = "div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2)";
////        driver.navigate().back();
////        return price;
////    }
//
//    public void printCheapestAndMostExpensiveCourses() throws InterruptedException, ExecutionException {
//        List<WebElement> courses = getCourses();
//
//        ExecutorService executor = Executors.newFixedThreadPool(5); // 5 потоков для параллельной работы
//
//        List<CompletableFuture<Map.Entry<String, BigDecimal>>> futures = courses.stream()
//                .map(course -> CompletableFuture.supplyAsync(() -> {
//                    String title = course.getText();
//                    BigDecimal price = getCoursePrice(course);
//                    return Map.entry(title, price);
//                }, executor))
//                .collect(Collectors.toList());
//
//        // Собираем результаты
//        List<Map.Entry<String, BigDecimal>> results = new ArrayList<>();
//        for (CompletableFuture<Map.Entry<String, BigDecimal>> future : futures) {
//            results.add(future.get()); // get() ждёт завершения каждой задачи
//        }
//
//        executor.shutdown();
//
//        // Находим самый дешевый и самый дорогой курс
//        Map.Entry<String, BigDecimal> cheapest = results.stream()
//                .min(Map.Entry.comparingByValue())
//                .orElseThrow();
//
//        Map.Entry<String, BigDecimal> mostExpensive = results.stream()
//                .max(Map.Entry.comparingByValue())
//                .orElseThrow();
//
//        System.out.println("Самый дешевый курс: " + cheapest.getKey() + " — " + cheapest.getValue());
//        System.out.println("Самый дорогой курс: " + mostExpensive.getKey() + " — " + mostExpensive.getValue());
//    }
//}
