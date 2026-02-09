# Koala Task Management - AI Coding Agent Instructions

## Project Overview
Koala is a **command-line task management application** for creating, tracking, and searching tasks. It supports three task types: generic Todos, Deadlines (with `/by` dates), and Events (with `/from` and `/to` times). The application persists tasks to disk and runs until the user enters "bye".

## Architecture (4-Layer Pattern)

**Data Model Layer** → [src/main/java/koala/task/](src/main/java/koala/task/)
- `Task`: Abstract base with `description` and `isComplete` state
- Subclasses: `Todo`, `Deadline`, `Event` (override `toString()` and `toStoreString()` for formatting)
- All task constructors validate non-empty descriptions; throw `InvalidTaskException` on invalid input

**Business Logic Layer** → [src/main/java/koala/TaskList.java](src/main/java/koala/TaskList.java)
- Wraps `ArrayList<Task>` with methods: `addTask()`, `deleteTask()`, `findTasks()`, `getTaskByIndex()`
- Case-insensitive keyword search in descriptions

**Persistence Layer** → [src/main/java/koala/Storage.java](src/main/java/koala/Storage.java)
- Serializes tasks to file using `toStoreString()` format
- Auto-creates missing directories/files on first load
- Throws `IOException` and `InvalidTaskException` (loaded via `parseTask()` method)

**Presentation & Control** → [src/main/java/koala/Parser.java](src/main/java/koala/Parser.java) + [src/main/java/koala/UI.java](src/main/java/koala/UI.java)
- `Parser.run()`: Main loop that reads user input, dispatches to handlers (`mark()`, `delete()`, `find()`, etc.), saves after each command
- `UI`: Static ANSI color formatting (dividers, GREEN for success, RED for errors, BLUE for echoing input)

**Entry Point**: [src/main/java/koala/Koala.java](src/main/java/koala/Koala.java) initializes Storage with `../data/koala.txt`

## Critical Developer Workflows

### Build & Run
```bash
# Gradle tasks (defined in build.gradle)
./gradlew build        # Compile + test
./gradlew run          # Run with stdin (requires input)
./gradlew shadowJar    # Build fat JAR (koala.jar in build/libs/)
./gradlew test         # Run JUnit 5 tests with detailed logging
```

### Adding New Commands
1. Add handler method to `Parser` (e.g., `private void newCommand(String input) throws InvalidTaskException`)
2. Add `if (input.startsWith("..."))` branch in `handleCommand()` before final exception
3. Update `InvalidTaskException` messages for validation errors

### Adding Task Subtypes
1. Extend `Task` class, implement custom `toString()` and `toStoreString()`
2. Register in `Storage.parseTask()` to deserialize from file format
3. Add creation command to `Parser.handleCommand()`

## Project-Specific Patterns & Conventions

**Error Handling**: Use `InvalidTaskException(String message)` for all validation failures; `IOException` for file issues. Parser catches these in its main loop to show user-friendly errors.

**Command Parsing**: Simple string splitting (e.g., `input.split(" /by ")`) rather than regex. Index-based commands (mark/delete) use `Integer.parseInt(input.split(" ")[1]) - 1` (user-facing indices are 1-indexed).

**UI Output**: Every user-facing message wrapped in `divider()` calls with consistent color coding. System prints directly to System.out; no logging framework.

**File Format**: Each task serialized as one line (see `toStoreString()` implementations). Storage preserves file path relative to Java working directory (`../data/koala.txt`).

**Testing**: JUnit 5 tests in [src/test/java/koala/](src/test/java/koala/) use `@Test` annotations. Gradle runs tests with `useJUnitPlatform()` and detailed logging. See [TaskListTest.java](src/test/java/koala/TaskListTest.java) for patterns.

## Integration Points
- **User Input**: `Scanner(System.in)` in `Parser.run()` reads line-by-line
- **Task Persistence**: `storage.saveTasks(taskList.getTasks())` after every command
- **Task Creation**: New task types added to Parser's if-chain and Storage's parseTask()

## Before Modifying
- Maintain 1-indexed user-facing task numbers (e.g., "task 1") while using 0-indexed ArrayList internally
- Always validate input before creating tasks; throw InvalidTaskException with descriptive messages
- Save tasks immediately after mutations (Parser already does this)
- Test new commands with text-ui-test workflow if applicable (see runtest.sh)
