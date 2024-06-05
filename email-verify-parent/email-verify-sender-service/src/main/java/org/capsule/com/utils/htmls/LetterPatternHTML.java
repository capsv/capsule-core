package org.capsule.com.utils.htmls;

public class LetterPatternHTML {

    public static String generate(String username, String code){
        return "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
            "<style>" +
            "body {font-family: Arial, sans-serif;}" +
            ".container {max-width: 600px; margin: auto; padding: 20px; border: 1px solid #dcdcdc; border-radius: 10px;}" +
            ".header {background-color: #f7f7f7; padding: 10px; text-align: center; border-bottom: 1px solid #dcdcdc;}" +
            ".content {padding: 20px;}" +
            ".code {font-size: 24px; font-weight: bold; color: #333;}" +
            ".footer {background-color: #f7f7f7; padding: 10px; text-align: center; border-top: 1px solid #dcdcdc;}" +
            "</style>" +
            "</head>" +
            "<body>" +
            "<div class='container'>" +
            "<div class='header'><h1>Confirmation Code</h1></div>" +
            "<div class='content'>" +
            "<p>Dear "+ username +",</p>" +
            "<p>Thank you for registering. Please use the following code to complete your registration:</p>" +
            "<p class='code'>" + code + "</p>" +
            "<p>If you did not request this code, please ignore this email.</p>" +
            "</div>" +
            "<div class='footer'><p>&copy; 2024 capsule. All rights reserved.</p></div>" +
            "</div>" +
            "</body>" +
            "</html>";
    }
}
