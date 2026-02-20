# Koala Task Manager – GMFD User Guide

Koala is a command-line task manager for tracking todos, deadlines, and events.  
All commands are **case-sensitive**. Dates and times can be entered in multiple formats.

---

## 1. General Command Structure

<command> [arguments]

Commands: todo, deadline, event, list, mark, unmark, delete, find, schedule, help, bye

---

## 2. Adding Tasks

### 2.1 Todo Task
Command:
todo <description>

Example:
todo Read chapter 5

Output:
Got it. I've added this task:
  [T][ ] Read chapter 5

---

### 2.2 Deadline Task
Command:
deadline <description> /by <due date/time>

Accepted date formats:
- yyyy-MM-dd → 2026-02-20
- dd/MM/yyyy → 20/02/2026
- MMM dd yyyy → Feb 20 2026
- Optional time: yyyy-MM-dd HH:mm → 2026-02-20 14:00

Example:
deadline Submit report /by 20/02/2026 23:59

Output:
Got it. I've added this task:
  [D][ ] Submit report (by: Feb 20 2026 23:59)

---

### 2.3 Event Task
Command:
event <description> /from <start date/time> /to <end date/time>

Example:
event Team meeting /from 2026-02-20 10:00 /to 2026-02-20 12:00

Output:
Got it. I've added this task:
  [E][ ] Team meeting (from: Feb 20 2026 10:00 to: Feb 20 2026 12:00)

---

## 3. Listing Tasks
Command:
list

Example Output:
Here are the tasks in your list:
Nice! You have work to do! Meanwhile, I'm just gonna sleep and eat LOL
1. [T][ ] Read chapter 5
2. [D][ ] Submit report (by: Feb 20 2026 23:59)
3. [E][ ] Team meeting (from: Feb 20 2026 10:00 to: Feb 20 2026 12:00)

---

## 4. Marking Tasks

### 4.1 Mark as Complete
mark <task number>

Example:
mark 2

Output:
Good job on finishing this task! I aspire to be like you but I'd rather sleep.
[D][X] Submit report (by: Feb 20 2026 23:59)

### 4.2 Mark as Incomplete
unmark <task number>

Example:
unmark 2

Output:
ahh! what happened? did you think you will do it and then backed out?
I've marked this task as not done:
[D][ ] Submit report (by: Feb 20 2026 23:59)

---

## 5. Deleting Tasks
delete <task number>

Example:
delete 1

Output:
Nice! Less work. More sleep! I've removed this task:
[T][ ] Read chapter 5

---

## 6. Searching Tasks
find <keyword>

Example:
find report

Output:
Here are the matching tasks in your list:
1. [D][ ] Submit report (by: Feb 20 2026 23:59)

---

## 7. Viewing Schedule by Date (includes deadlines that occur before the date)
schedule <date>

Accepted date formats:
- yyyy-MM-dd → 2026-02-20
- dd/MM/yyyy → 20/02/2026
- MMM dd yyyy → Feb 20 2026

Example:
schedule 2026-02-20

Output:
Here are the tasks scheduled for 2026-02-20:
2. [D][ ] Submit report (by: Feb 20 2026 23:59)
3. [E][ ] Team meeting (from: Feb 20 2026 10:00 to: Feb 20 2026 12:00)

No tasks scheduled example:
No tasks scheduled for Feb 20 2026.

---

## 8. Help Command
help

Output:
Available commands:
1. list - List all tasks
2. todo <description> - Add a todo task
3. deadline <description> /by <time> - Add a deadline task
4. event <description> /from <start> /to <end> - Add an event task
5. mark <task number> - Mark a task as complete
6. unmark <task number> - Mark a task as incomplete
7. delete <task number> - Delete a task
8. find <keyword> - Find tasks containing the keyword
9. schedule <date> - View tasks scheduled for a specific date
10. bye - Exit the application

---

## 9. Exiting Koala
bye

Output:
Bye. Hope to see you again soon!

---

## 10. Notes & Tips
- Task numbers start from 1.  
- Omit time in dates → assumed as 00:00 (midnight).  
- Event start must be before end.  
- Deadline/Event dates are flexible; multiple formats accepted.  
- Always provide a description; empty descriptions throw errors.  
- Tasks are auto-saved after each command.
