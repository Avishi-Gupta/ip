# Koala Task Manager – User Guide


## Introduction

Koala Task Manager is a CLI-based task tracking application that allows users to manage todos, deadlines and events efficiently through simple text commands.  

This guide explains all available features, command formats and example outputs.

---

## Table of Features

| Feature | Description |
|----------|-------------|
| [Add Todo](#add-todo) | Add a simple task without date |
| [Add Deadline](#add-deadline) | Add a task with a due date |
| [Add Event](#add-event) | Add a task with start and end time |
| [List Tasks](#list-tasks) | View all tasks |
| [Mark Task](#mark-task) | Mark task as complete |
| [Unmark Task](#unmark-task) | Mark task as incomplete |
| [Delete Task](#delete-task) | Remove a task |
| [Find Tasks](#find-tasks) | Search tasks by keyword |
| [Schedule View](#schedule-view) | View tasks on a specific date |
| [Help](#help) | View all commands |
| [Exit](#exit) | Exit the application |

---

[Jump to Summary Table](#summary-table)

---

# Features

---

## Add Todo

**Command:**

```
todo <description>
```

**Example:**

```
todo Read chapter 5
```

**Output:**

```bash
[T][ ] Read chapter 5
```

---

## Add Deadline

**Command:**

```
deadline <description> /by <date/time>
```

Accepted formats:
- yyyy-MM-dd
- dd/MM/yyyy
- MMM dd yyyy
- Optional time: HH:mm

**Example 1:**

```
deadline Submit report /by 20/02/2026
```

```bash
[D][ ] Submit report (by: Feb 20 2026 00:00)
```

**Example 2:**

```
deadline Submit report /by 20/02/2026 23:59
```

```bash
[D][ ] Submit report (by: Feb 20 2026 23:59)
```

---

## Add Event

**Command:**

```
event <description> /from <start> /to <end>
```

**Example:**

```
event Team meeting /from 2026-02-20 10:00 /to 2026-02-20 12:00
```

```bash
[E][ ] Team meeting (from: Feb 20 2026 10:00 to: Feb 20 2026 12:00)
```

---

## List Tasks

**Command:**

```
list
```

**Example Output:**

```bash
1. [T][ ] Read chapter 5
2. [D][ ] Submit report (by: Feb 20 2026 23:59)
3. [E][ ] Team meeting (from: Feb 20 2026 10:00 to: Feb 20 2026 12:00)
```

---

## Mark Task

**Command:**

```
mark <task number>
```

**Example:**

```
mark 2
```

```bash
[D][X] Submit report (by: Feb 20 2026 23:59)
```

---

## Unmark Task

**Command:**

```
unmark <task number>
```

**Example:**

```
unmark 2
```

```bash
[D][ ] Submit report (by: Feb 20 2026 23:59)
```

---

## Delete Task

**Command:**

```
delete <task number>
```

**Example:**

```
delete 1
```

```bash
[T][ ] Read chapter 5 removed
```

---

## Find Tasks

**Command:**

```
find <keyword>
```

**Example:**

```
find report
```

```bash
[D][ ] Submit report (by: Feb 20 2026 23:59)
```

---

## Schedule View

**Command:**

```
schedule <date>
```

**Example:**

```
schedule 2026-02-20
```

```bash
2. [D][ ] Submit report (by: Feb 20 2026 23:59)
3. [E][ ] Team meeting (from: Feb 20 2026 10:00 to: Feb 20 2026 12:00)
```

If no tasks:

```bash
No tasks scheduled for Feb 20 2026.
```

---

## Help

**Command:**

```
help
```

```bash
Displays all available commands and usage instructions.
```

---

## Exit

**Command:**

```
bye
```

```bash
Bye. Hope to see you again soon!
```

---

# Summary Table

| Command | Format |
|----------|--------|
| **todo** | todo `<description>` |
| **deadline** | deadline `<description>` /by `<date/time>` |
| **event** | event `<description>` /from `<start>` /to `<end>` |
| **list** | list |
| **mark** | mark `<task#>` |
| **unmark** | unmark `<task#>` |
| **delete** | delete `<task#>` |
| **find** | find `<keyword>` |
| **schedule** | schedule `<date>` |
| **help** | help |
| **bye** | bye |

---

# Tips

- Task numbers start from **1**
- Omitting time defaults to **00:00 (midnight)**
- Event start time must be **before end time**
- Descriptions cannot be empty
- Tasks are automatically saved after each command
- Supported date formats:
  - `yyyy-MM-dd`
  - `dd/MM/yyyy`
  - `MMM dd yyyy`
  - Optional `HH:mm`

---
