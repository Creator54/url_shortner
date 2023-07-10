#### URL Shortener

A basic URL shortener made with Java and Gradle.

#### How it Works
  * The application takes two command-line parameters: operation and value/URL seperated via ",".
  * The operation can be either add or remove.
  * If the operation is add, the application creates a random 5-character file with basic HTML content for redirection.
  * If a redirect already exists, it does not create a duplicate and displays the existing redirect.
  * If the operation is remove, either the URL or the redirect/short-name can be passed to remove the redirect.
  * The redirects folder can be hosted using an Nginx webserver configured as a file browser with the following configuration:

```nginx
root /path/to/redirects_folder;
index index.html index.htm;
default_type text/html;
autoindex on;
```

#### Dependencies
```openjdk```

#### Usage

To run the application, use the following command:
```./gradlew run -Pargs="add,https://github.com/someUser/somelong/long/URL"```

Make sure to update the /path/to/redirects_folder in the Nginx configuration with the actual path to your redirects folder.

#### Demo
[demo_url](https://go.creator54.dev)

#### LICENSE

Unlicense
