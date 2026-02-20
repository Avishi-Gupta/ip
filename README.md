# **Koala Task Manager 

**Commands & Syntax:**

---

**1. Add Todo**
todo <description>  
**Example:** todo Read chapter 5  
**Output:** [T][ ] Read chapter 5

---

**2. Add Deadline**
deadline <description> /by <due date/time>  
**Accepted formats:** yyyy-MM-dd, dd/MM/yyyy, MMM dd yyyy  
Optional time: yyyy-MM-dd HH:mm  
**Example:** deadline Submit report /by 20/02/2026 23:59  
**Output:** [D][ ] Submit report (by: Feb 20 2026 23:59)

---

**3. Add Event**
event <description> /from <start date/time> /to <end date/time>  
**Example:** event Team meeting /from 2026-02-20 10:00 /to 2026-02-20 12:00  
**Output:** [E][ ] Team meeting (from: Feb 20 2026 10:00 to: Feb 20 2026 12:00)

---

**4. List Tasks**
list  
**Output Example:**  
1. [T][ ] Read chapter 5  
2. [D][ ] Submit report (by: Feb 20 2026 23:59)  
3. [E][ ] Team meeting (from: Feb 20 2026 10:00 to: Feb 20 2026 12:00)

---

**5. Mark Task as Complete**
mark <task number>  
**Example:** mark 2  
**Output:** [D][X] Submit report (by: Feb 20 2026 23:59)

**6. Mark Task as Incomplete**
unmark <task number>  
**Example:** unmark 2  
**Output:** [D][ ] Submit report (by: Feb 20 2026 23:59)

---

**7. Delete Task**
delete <task number>  
**Example:** delete 1  
**Output:** [T][ ] Read chapter 5 removed

---

**8. Find Tasks**
find <keyword>  
**Example:** find report  
**Output:** [D][ ] Submit report (by: Feb 20 2026 23:59)

---

**9. View Schedule**
schedule <date>  
**Accepted formats:** yyyy-MM-dd, dd/MM/yyyy, MMM dd yyyy  
**Example:** schedule 2026-02-20  
**Output Example:**  
2. [D][ ] Submit report (by: Feb 20 2026 23:59)  
3. [E][ ] Team meeting (from: Feb 20 2026 10:00 to: Feb 20 2026 12:00)  
**No tasks example:** No tasks scheduled for Feb 20 2026.

---

**10. Help**
help  
**Output:** Lists all commands and usage.

---

**11. Exit**
bye  
**Output:** Bye. Hope to see you again soon!

---

**Tips:**  
- Task numbers start from 1.  
- Omit time → assumed 00:00 (midnight).  
- Event start must be before end.  
- Descriptions cannot be empty.  
- Tasks auto-save after each command.  
- Multiple date formats are supported for deadlines/events.
