# AI Doc Generator

A Spring Boot service that takes raw, undocumented code and sends it back with generated JavaDoc, using Gemini as the underlying model.

The REST layer itself is nothing special - one endpoint, one DTO in, one DTO out. The part actually worth reading is the boundary between the app and the LLM: how the prompt gets built, how it's translated into Gemini's specific request format, and what happens when that call fails.

A request comes into `DocumentationController`, gets validated (code can't be blank, there's a length cap so nobody pastes in a 50k-line file), and goes to `DocumentationServiceImpl`. From there `PromptBuilder` puts together the actual prompt sent to the model. The system prompt itself lives in a plain text file under `resources/prompts`, not as a string literal in Java, so it can be tweaked without recompiling anything - which matters more than it sounds, because getting the model to reliably return only code and nothing else took a few rounds of rewording.

`GeminiClient` is where most of the actual work happened. It implements a small `AiClient` interface, so in theory the model behind it is swappable, but the reason that interface exists is really Gemini's API being different enough from the OpenAI-style convention that most other providers copy. Requests are shaped as `contents`/`parts` instead of a `messages` array, and auth goes through an `x-goog-api-key` header instead of a bearer token - small things, but they're exactly the kind of small things that cost an hour of staring at a 401 before you notice.

Whatever goes wrong on that path - bad key, rate limit, Gemini returning something unexpected - gets wrapped into an `AiServiceException` and caught by a global exception handler, which turns it into a proper error response instead of a raw stack trace going back to whoever called the endpoint.

## Stack

Java 25, Spring Boot 4.1.1, WebClient for the outbound call to Gemini, Bean Validation, Lombok. Model is `gemini-3.1-flash-lite` - cheap and free-tier friendly, and honestly more than enough for a task like "add comments to this code", nothing here needs a reasoning-heavy model.

## Running it

Just needs a JDK, Maven wrapper is checked in so nothing to install separately.

```bash
cp .env.example .env
# put a real Gemini API key in .env

export $(cat .env | xargs)
./mvnw spring-boot:run
```

Runs on localhost:8080.

## Trying it

```bash
curl -X POST http://localhost:8080/api/v1/docs/generate \
  -H "Content-Type: application/json" \
  -d '{
        "code": "public class Foo { public int add(int a, int b) { return a + b; } }",
        "language": "java",
        "style": "javadoc"
      }'
```

You get the same code back with JavaDoc added on the class and the method. Send blank code and you get a 400 with a field-level message instead of the request silently going through.

