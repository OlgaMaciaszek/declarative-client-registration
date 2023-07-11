package demo.client;

import java.net.URL;

/**
 * @author Olga Maciaszek-Sharma
 */
public record Newspaper(String lccn, String state, URL url, String title) {
}
