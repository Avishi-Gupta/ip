# **Koala Task Manager**

| **Command** | **Syntax** | **Example** | **Output** |
|-------------|------------|-------------|------------|
| **Add Todo** | todo <description> | todo Read chapter 5 | [T][ ] Read chapter 5 |
| **Add Deadline** | deadline <desc> /by <date/time> | deadline Submit report /by 20/02/2026 23:59 | [D][ ] Submit report (by: Feb 20 2026 23:59) |
| **Add Event** | event <desc> /from <start> /to <end> | event Team meeting /from 2026-02-20 10:00 /to 2026-02-20 12:00 | [E][ ] Team meeting (from: Feb 20 2026 10:00 to: Feb 20 2026 12:00) |
| **List Tasks** | list | list | 1. [T][ ] Read chapter 5<br/>2. [D][ ] Submit report (by: Feb 20 2026 23:59)<br/>3. [E][ ] Team meeting (from: Feb 20 2026 10:00 to: Feb 20 2026 12:00) |
| **Mark Complete** | mark <task#> | mark 2 | [D][X] Submit report (by: Feb 20 2026 23:59) |
| **Mark Incomplete** | unmark <task#> | unmark 2 | [D][ ] Submit report (by: Feb 20 2026 23:59) |
| **Delete Task** | delete <task#> | delete 1 | [T][ ] Read chapter 5 removed |
| **Find Tasks** | find <keyword> | find report | [D][ ] Submit report (by: Feb 20 2026 23:59) |
| **Schedule** | schedule <date> | schedule 2026-02-20 | 2. [D][ ] Submit report (by: Feb 20 2026 23:59)<br/>3. [E][ ] Team meeting (from: Feb 20 2026 10:00 to: Feb 20 2026 12:00) |
| **Help** | help | help | Lists all commands |
| **Exit** | bye | bye | Bye. Hope to see you again soon! |

**Tips:**  
- Task numbers start from 1
- Omitting time → assumed **00:00 (midnight)**  
- Event start must be before end
- Descriptions cannot be empty  
- Tasks auto-save after every command  
- Multiple date formats supported: `yyyy-MM-dd`, `dd/MM/yyyy`, `MMM dd yyyy`, optionally with `HH:mm`
