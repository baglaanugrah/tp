---

---
layout: default.md
title: "User Guide"
pageNav: 3
---

# TeamEventPro User Guide

TeamEventPro is a **desktop** app for managing tech meetup attendees,
optimized for use via a Command Line Interface (CLI) while still having the benefits
of a Graphical User Interface (GUI). If you can type fast, TeamEventPro can get 
your event management tasks done faster than traditional GUI apps.
---

## Quick start

1. Ensure you have Java `17` or above installed on your computer.

  **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).
2. Download the latest `.jar` file from [Releases](https://github.com/AY2526S2-CS2103T-W11-1/tp/releases).
3. Copy the file to the folder you want to use as the *home folder* for TeamEventPro. 
4. Open a command terminal, `cd` into the folder containing the jar file, and run `java -jar addressbook.jar`.  

  A GUI similar to the below should appear in a few seconds.   

![Ui](images/Ui.png)
5. Type commands in the command box and press Enter to execute them. For example, typing `help` and pressing Enter opens the help window.  

  Some example commands you can try:
  - `addevent n/Tech Meetup d/2026-06-15 l/NUS Techno Edge desc/Annual networking session` : Adds an event.
  - `list` : Lists all events in the event list view.
  - `search tech` : Searches the displayed event list for matching events.
  - `enter event 1` : Enters the 1st event so applicant commands operate on that event's participant list.
  - `deleteevent 2` : Deletes the 2nd event and its participant list.
  - `list` : Lists all participants in the current event.
  - `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 tm/Dev g/johndoe r/yes` : Adds an applicant with team, GitHub, and RSVP status.
  - `filter r/yes` : Filters to show only applicants who have RSVP'd yes.
  - `checkin 1` : Marks the 1st applicant in the current list as checked in.
  - `assign 2 team/Alpha` : Assigns the 2nd applicant to team Alpha.
  - `delete 3` : Deletes the 3rd applicant shown in the current list.
  - `switchmode light` : Switches the app to light mode.
  - `leave` : Returns to the event list.
  - `exit` : Exits the app.
6. Refer to the [Features](#features) below for details of each command.

---

## Features



**Notes about the command format:**  


- Words in `UPPER_CASE` are the parameters to be supplied by the user.  
  
e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.
- Items in square brackets are optional.  
  
e.g. `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.
- Items with `…`​ after them can be used multiple times including zero times.  
  
e.g. `[t/TAG]…​` can be used as  `` (i.e. 0 times), `t/friend`, `t/friend t/colleague` etc.
- Parameters can be in any order.  
  
e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.
- Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.  
  
e.g. if the command specifies `help 123`, it will be interpreted as `help`.
- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

### Viewing help : `help`

Shows a message explaining how to access the help page.

Format: `help`

### Switching theme mode : `switchmode`

Switches the application between dark mode and light mode.

Format: `switchmode THEME`

- `THEME` must be either `dark` or `light`.
- This command can be used in both the event list view and the participant list view.
- If you are already in the requested theme, the app will show `You are already in dark mode.` or
  `You are already in light mode.`

Examples:

- `switchmode dark`
- `switchmode light`

### Adding an event : `addevent`

Adds an event to the event book.

Format: `addevent n/NAME d/DATE [l/LOCATION] [desc/DESCRIPTION]`

- `DATE` must be in `YYYY-MM-DD` format.
- You must be in the event list view to use this command.

Examples:

- `addevent n/Tech Meetup d/2026-06-15`
- `addevent n/Hack Night d/2026-08-20 l/NUS COM1 desc/Bring your laptop`

### Editing an event : `editevent`

Edits an existing event in the event list. Existing values will be overwritten by the input values.

Format: `editevent INDEX [n/NAME] [d/DATE] [l/LOCATION] [desc/DESCRIPTION]`

- Edits the event at the specified `INDEX`. The index refers to the index number shown in the displayed event list.
- The index **must be a positive integer** 1, 2, 3, …
- At least one optional field must be provided.
- You must be in the event list view to use this command.
- You can clear the location by typing `l/` with nothing after it.
- You can clear the description by typing `desc/` with nothing after it.

Examples:

- `editevent 1 n/Tech Meetup 2026`
- `editevent 2 d/2026-09-01 l/NUS Innovation 4.0`
- `editevent 3 l/ desc/`

### Deleting an event : `deleteevent`

Deletes an existing event from the event list. The participant list stored under that event will be deleted together
with it.

Format: `deleteevent INDEX`

- Deletes the event at the specified `INDEX`. The index refers to the index number shown in the displayed event list.
- The index **must be a positive integer** 1, 2, 3, …
- You must be in the event list view to use this command.

Examples:

- `deleteevent 1`
- `deleteevent 3`

### Entering an event : `enter`

Switches the app into the participant list of the selected event.

Format: `enter event INDEX`

- The index refers to the index number shown in the displayed event list.
- The index **must be a positive integer** 1, 2, 3, …

Example:

- `enter event 1`

### Leaving an event : `leave`

Returns from the participant list view to the event list.

Format: `leave`

### Adding an applicant: `add`

Adds an applicant to the address book. Supports GitHub username and RSVP status in addition to core contact fields.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [tm/TEAM] [g/GITHUB_USERNAME] [r/RSVP_STATUS] [t/TAG]…​`

- You must enter an event first using `enter event INDEX`.
- `RSVP_STATUS` must be `yes`, `no`, or `pending` (case-insensitive).
- `TEAM` must be alphanumeric and at most 15 characters.



**Tip:** An applicant can have any number of tags (including 0)  






Examples:

- `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25`
- `add n/Betsy Crowe p/1234567 e/betsy@example.com a/Newgate Prison tm/Development g/betsycrowe r/yes t/Python t/ML`

### Listing all events or applicants : `list`

Lists all events in the event list view, or all applicants in the currently entered event.

Format: `list`

- In the event list view, `list` shows all events.
- After `enter event INDEX`, `list` shows all applicants in the current event.
- `list` clears any active `search` results in the current view and restores the full list.

Examples:

- `list` — Shows all events when you are in the event list view.
- `enter event 1` followed by `list` — Shows all applicants in the 1st event.

### Importing applicants from CSV : `import`

Imports applicants from a CSV file into the currently entered event.

Format: `import FILE_PATH` or `import list`

- You must enter an event first using `enter event INDEX`.
- The file path must point to a `.csv` file.
- Use `import list` (or `import` with no path) to list discoverable `.csv` files and their directories.
- Required CSV headers: `name,phone,email,address`.
- Optional CSV headers: `team,github,rsvpStatus,tags,checkinStatus`.
- Duplicate applicants are skipped.
- Invalid rows are skipped and reported in the command result.
- CSV field validation uses the same rules as applicant commands where applicable:
  - `name`, `phone`, `email`, `address`, `team`, `github`, `rsvpStatus`, `tags`: follow the same validation as fields used in [`add`](#adding-an-applicant-add) and [`edit`](#editing-an-applicant--edit).
  - `rsvpStatus`: valid values are `yes`, `no`, `pending` (same as [`add`](#adding-an-applicant-add) and [`filter`](#filtering-applicants-by-rsvp-or-tag--filter)).
  - `team`: must be alphanumeric and at most 15 characters (same as [`add`](#adding-an-applicant-add) and [`assign`](#assigning-a-team--assign)).
  - `tags`: use `;` as the separator in CSV, for example `Python;ML`.
  - `checkinStatus` valid values:
    - Checked in: `yes`, `checked-in`, `true`
    - Not checked in: `no`, `not checked-in`, `false`

Sample CSV:

```csv
name,phone,email,address,team,github,rsvpStatus,tags,checkinStatus
John Doe,98765432,johnd@example.com,"311, Clementi Ave 2, #02-25",Development,johndoe,yes,Python;ML,checked-in
Jane Tan,91234567,jane@example.com,5 Sports Hub Ave,,,no,,false
```

Example:

- `import data/participants.csv`
- `import list`

### Exporting applicants to CSV : `export`

Exports all applicants in the currently entered event to a CSV file.

Format: `export [FILE_PATH]`

- You must enter an event first using `enter event INDEX`.
- If `FILE_PATH` is omitted, default path is `data/exports/participants.csv`.
- If the target file already exists, a timestamped file is created automatically.

Examples:

- `export`
- `export data/exports/hacknight.csv`

### Editing an applicant : `edit`

Edits an existing applicant in the address book. (Renamed from `modify`.) Updates an applicant using their list index; you can change one or more fields in a single command.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [tm/TEAM] [g/GITHUB_USERNAME] [r/RSVP_STATUS] [t/TAG]…​`

- Edits the applicant at the specified `INDEX`. The index refers to the index number shown in the displayed applicant list. The index **must be a positive integer** 1, 2, 3, …​
- At least one of the optional fields must be provided.
- Existing values will be overwritten by the input values.
- When editing tags, the existing tags of the applicant will be replaced (tags are not cumulative).
- You can remove all the applicant's tags by typing `t/` without specifying any tags after it.
- You can clear the team by typing `tm/` with nothing after it.
- You must enter an event first using `enter event INDEX`.

Examples:

- `edit 1 p/91234567 e/johndoe@example.com` — Edits the phone number and email of the 1st applicant.
- `edit 2 n/Betsy Crower t/` — Edits the name to `Betsy Crower` and clears all existing tags.
- `edit 3 r/yes tm/Alpha` — Updates RSVP to yes and assigns the applicant to team Alpha.

### Assigning a team : `assign`

Assigns an applicant to a specific hackathon team.

Format: `assign INDEX team/TEAM_NAME`

- The index refers to the index number shown in the displayed applicant list.
- The index **must be a positive integer** 1, 2, 3, …​
- `TEAM_NAME` must be alphanumeric and at most 15 characters.
- You must enter an event first using `enter event INDEX`.

Examples:

- `assign 2 team/Alpha`
- `assign 1 team/Team7`

### Filtering applicants : `filter`

Narrows the applicant list by a single criterion—either RSVP status or tag. You cannot filter by both at once.

Format: `filter r/RSVP_STATUS` or `filter t/TAG`

- `RSVP_STATUS` must be `yes`, `no`, or `pending` (case-insensitive).
- Use exactly one criterion per command.
- You must enter an event first using `enter event INDEX`.

Examples:

- `filter r/yes` — Shows only applicants who have RSVP'd yes.
- `filter r/pending` — Shows applicants with pending RSVP.
- `filter t/Python` — Shows applicants tagged with Python.

### Checking in an applicant : `checkin`

Marks a person as checked-in based on their position in the latest displayed list.

Format: `checkin INDEX`

- The index refers to the index number shown in the displayed applicant list.
- The index **must be a positive integer** 1, 2, 3, …​
- You must enter an event first using `enter event INDEX`.

Examples:

- `list` followed by `checkin 1` — Checks in the 1st applicant in the list.
- `filter r/yes` followed by `checkin 2` — Checks in the 2nd applicant from the filtered results.

### Searching events or applicants : `search`

Searches the currently active list using one or more keywords.

Format: `search KEYWORD [MORE_KEYWORDS]`

- The search is case-insensitive.
- The order of the keywords does not matter.
- Items matching at least one keyword will be returned (i.e. `OR` search).
- In the event list view, `search` looks through event `name`, `date`, `location`, and `description`.
- After `enter event INDEX`, `search` looks through applicant `name`, `phone`, `address`, `email`, `team`,
  `GitHub`, and `check-in status`.
- Matching uses case-insensitive substring matching on the relevant visible fields.

Examples:

- `search tech 2026-06-15` — Returns events whose details match either `tech` or `2026-06-15`.
- `enter event 1` followed by `search alex` — Returns applicants in the current event whose details match `alex`.
- `enter event 1` followed by `search lidavid checked-in` — Returns applicants in the current event matching either
  `lidavid` or `checked-in`.

### Deleting an applicant : `delete`

Deletes a person based on their position in the latest displayed list.

Format: `delete INDEX`

- Deletes the applicant at the specified `INDEX`.
- The index refers to the index number shown in the displayed applicant list.
- The index **must be a positive integer** 1, 2, 3, …​
- You must enter an event first using `enter event INDEX`.

Examples:

- `list` followed by `delete 2` — Deletes the 2nd applicant in the address book.
- `search Betsy` followed by `delete 1` — Deletes the 1st applicant in the search results.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

- You must enter an event first using `enter event INDEX`.

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

TeamEventPro data are saved to the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

TeamEventPro data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users can update data directly by editing that data file.



**Caution:**  
If your changes to the data file make its format invalid, TeamEventPro will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.  
  
Furthermore, certain edits can cause TeamEventPro to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.  

---

## FAQ

**Q**: How do I transfer my data to another computer?  
  
**A**: Install the app on the other computer and overwrite the empty data file it creates with the file that contains your previous TeamEventPro data.

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI may open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

---

## Command summary


| Action      | Format, Examples                                                                                                                                                                          |
| ----------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **AddEvent** | `addevent n/NAME d/DATE [l/LOCATION] [desc/DESCRIPTION]` e.g., `addevent n/Tech Meetup d/2026-06-15 l/NUS Techno Edge desc/Annual networking session`                                   |
| **EditEvent** | `editevent INDEX [n/NAME] [d/DATE] [l/LOCATION] [desc/DESCRIPTION]` e.g., `editevent 1 d/2026-09-01 l/NUS Innovation 4.0`                                                            |
| **DeleteEvent** | `deleteevent INDEX` e.g., `deleteevent 2`                                                                                                                                             |
| **Enter**   | `enter event INDEX` e.g., `enter event 1`                                                                                                                                                 |
| **Leave**   | `leave`                                                                                                                                                                                   |
| **Add**     | `add n/NAME p/PHONE e/EMAIL a/ADDRESS [tm/TEAM] [g/GITHUB] [r/RSVP] [t/TAG]…​` e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd tm/Dev g/jamesho r/yes t/Python` |
| **Assign**  | `assign INDEX team/TEAM_NAME` e.g., `assign 2 team/Alpha`                                                                                                                                 |
| **CheckIn** | `checkin INDEX` e.g., `checkin 1`                                                                                                                                                         |
| **Clear**   | `clear`                                                                                                                                                                                   |
| **Delete**  | `delete INDEX` e.g., `delete 3`                                                                                                                                                           |
| **Edit**    | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [tm/TEAM] [g/GITHUB] [r/RSVP] [t/TAG]…​` e.g., `edit 2 n/James Lee e/jameslee@example.com`                                           |
| **Filter**  | `filter r/RSVP` or `filter t/TAG` e.g., `filter r/yes`, `filter t/Python`                                                                                                                 |
| **Import**  | `import FILE_PATH` or `import list` e.g., `import data/participants.csv`, `import list`                                                                                                   |
| **List**    | `list`                                                                                                                                                                                    |
| **Search**  | `search KEYWORD [MORE_KEYWORDS]` e.g., `search tech meetup`                                                                                                                               |
| **SwitchMode** | `switchmode THEME` e.g., `switchmode light`                                                                                                                                           |
| **Export**  | `export [FILE_PATH]` e.g., `export`, `export data/exports/hacknight.csv`                                                                                                                  |                                                                                                                               |
| **Help**    | `help`                                                                                                                                                                                    |
| **Exit**    | `exit`                                                                                                                                                                                    |
