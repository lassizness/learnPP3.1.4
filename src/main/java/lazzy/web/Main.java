package lazzy.web;

import lazzy.web.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

public class Main {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        // Получение списка всех пользователей
        ResponseEntity<String> response1 = restTemplate.exchange(
                "http://94.198.50.185:7081/api/users",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );
        String sessionId = response1.getHeaders().getFirst("Set-Cookie");


        // Сохранение пользователя
        headers.set("Cookie", sessionId);
        User user = new User(3L, "Valera", "Valerkin", (byte) 25);
        //System.out.println(user.getId()+" "+user.getName()+" "+user.getLastName()+" "+ user.getAge());
        ResponseEntity<String> response2 = restTemplate.exchange(
                "http://94.198.50.185:7081/api/users",
                HttpMethod.POST,
                new HttpEntity<>(user, headers),
                String.class
        );

        // Изменение пользователя
        User updatedUser = new User(3L, "ivan", "ivanow", (byte) 30);
        ResponseEntity<String> response3 = restTemplate.exchange(
                "http://94.198.50.185:7081/api/users",
                HttpMethod.PUT,
                new HttpEntity<>(updatedUser, headers),
                String.class
        );

        // Удаление пользователя
        ResponseEntity<String> response4 = restTemplate.exchange(
                "http://94.198.50.185:7081/api/users/3",
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                String.class
        );

        // Конкатенация частей кода
        String code = response2.getBody() + response3.getBody() + response4.getBody();
        System.out.println("Итоговый код: " + code);
    }
}
