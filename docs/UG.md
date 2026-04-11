---
layout: default.md
title: "User Guide"
pageNav: 0
---

# TeamEventPro User Guide

## Guide Contents

- [About TeamEventPro](#1-about-teameventpro)
- [Getting Started](#getting-started)
- [Command Fundamentals](#command-fundamentals)
- [Common Commands](#common-commands)
  - [Help : `help`](#1-help-command)
  - [List : `list`](#2-list-command)
  - [Search : `search`](#3-search-command)
  - [Switch Theme : `switchtheme`](#4-switch-theme-command)
- [Event Commands](#event-commands)
  - [Add Event : `addevent`](#cmd-addevent)
  - [Edit Event : `editevent`](#cmd-editevent)
  - [Delete Event : `deleteevent`](#cmd-deleteevent)
  - [Enter Event : `enter event`](#cmd-enter-event)
  - [Exit : `exit`](#cmd-exit)
- [Participant Commands](#participant-commands)
  - [Add Participant : `add`](#cmd-add)
  - [Edit Participant : `edit`](#cmd-edit)
  - [Delete Participant : `delete`](#cmd-delete)
  - [Clear Participants : `clear`](#cmd-clear)
  - [Assign Team : `assign`](#cmd-assign)
  - [Check-In : `checkin`](#cmd-checkin)
  - [Filter : `filter`](#cmd-filter)
  - [View Participant : `view`](#cmd-view)
  - [Statistics : `statistics`](#cmd-statistics)
  - [Import : `import`](#cmd-import)
  - [Export : `export`](#cmd-export)
  - [Leave Event : `leave event`](#cmd-leave-event)

---

## 1. About TeamEventPro

TeamEventPro is a desktop application designed to help users manage events and participants efficiently.
It is intended for users who prefer typing commands over navigating through menus, allowing them to
perform tasks quickly and consistently.

The application supports two main workflows:

- **Event management**
- **Participant management (inside an event)**

TeamEventPro provides the speed of a Command Line Interface (CLI) while still offering the visual clarity
of a Graphical User Interface (GUI). This makes it suitable for users who want a structured and efficient
way to handle event and participant management in a single application.

---

## 2. Understanding App Modes

TeamEventPro has two main modes of use.

### 2.1 Outside an event

In this mode, you are viewing and managing the list of events.

You can use this mode to:
- create events
- edit event details
- delete events
- search for events
- enter a specific event

Full details for these commands are in [Event Commands](#event-commands).

### 2.2 Inside an event

In this mode, you are managing participants within a selected event.

You can use this mode to:
- add, edit, and delete participants
- assign participants to teams
- check in participants
- filter and view participant details
- view event statistics
- import and export participant data
- leave the current event and return to the event list

Full details for these commands are in [Participant Commands](#participant-commands).

## 3. Commands Available in Both Modes

The following commands can be used regardless of whether you are inside or outside an event:

- `help`
- `list`
- `search`
- `switchtheme`

Full details for these commands are in [Common Commands](#common-commands).


---

# Getting Started

This page helps you install TeamEventPro, launch it, and complete your first workflow.

---

## 1. Prerequisites

- Install Java `17` or above.
- Ensure your terminal can run `java -version`.

For macOS-specific setup guidance, follow the prescribed JDK instructions in the project docs.

---

## 2. Install and launch

1. Download the latest TeamEventPro `.jar` from the release page.
2. Place the `.jar` file in your preferred working folder.
3. Open a terminal in that folder.
4. Run:

   `java -jar addressbook.jar`

5. Wait for the application window to open.

---

## 3. First-time setup

- On first launch, complete the onboarding tutorial.
- Use the command box at the bottom of the app to run commands.
- Press Enter after each command.

---

## 4. Where to go next

- For shared command conventions, prefix usage, and index/list behavior, see [Command Fundamentals](#command-fundamentals).
- View global commands in [Common Commands](#common-commands).
- View mode-specific command details in [Event Commands](#event-commands) and
  [Participant Commands](#participant-commands).

---

# Command Fundamentals

This page is the shared conventions reference for how commands are written and interpreted across TeamEventPro.
Read this once before using [Event Commands](#event-commands) or [Participant Commands](#participant-commands).

---

## 1. Command Notation

- Words in `UPPER_CASE` are parameters to be supplied by the user.
- Items followed by `...` can be used multiple times.
- For prefixed arguments, parameter order usually does not matter unless stated otherwise.
- Indexes refer to the numbers shown in the displayed list.
- Dates should follow the format `YYYY-MM-DD`.

---

## 2. Command Structure and Modes

TeamEventPro operates in two modes:
- **Outside an event**: event-level commands such as `addevent`, `editevent`, `deleteevent`, `enter event`, `list`, `search`.
- **Inside an event**: participant-level commands such as `add`, `edit`, `delete`, `assign`, `filter`, `checkin`, `view`, `statistics`, `list`, `search`, `leave event`.

Most commands follow one of these patterns:
- `COMMAND [INDEX] [PREFIX/VALUE]...`
- `COMMAND KEYWORD INDEX` (example: `enter event 1`)
- `COMMAND` (example: `list`, `help`, `statistics`)

---

## 3. Prefix Reference

| Prefix | Field | Accepts | Does not accept |
| --- | --- | --- | --- |
| `n/` | Name | Alphanumeric characters, spaces, hyphens (`-`), slashes (`/`), and apostrophes (`'`), e.g. `n/John Doe`, `n/John-Doe`, `n/John/Ong`, `n/John O'Neil` | Other special characters (for example `@`, `#`, `%`, `!`) |
| `p/` | Phone | Digits only, at least 3 digits, e.g. `p/98765432` | Letters/symbols, e.g. `p/98A76`, `p/+6598765432` |
| `e/` | Email | Standard email format, e.g. `e/john@example.com` | Missing `@` or invalid format, e.g. `e/johnexample.com` |
| `a/` | Address | Free-text address, e.g. `a/311 Clementi Ave 2` |  |
| `tm/` | Team (`add`/`edit`) | Alphanumeric team name, 1-15 chars, e.g. `tm/Alpha7` | Spaces/symbols/too-long text, e.g. `tm/Alpha Team`, `tm/Alpha-1` |
| `team/` | Team (`assign`/`filter`) | Alphanumeric team name, 1-15 chars, e.g. `team/Alpha7` | Using `tm/` in `assign`/`filter`; invalid team format |
| `g/` | GitHub username | GitHub-style username, e.g. `g/johndoe`, `g/john-doe` | Leading/trailing hyphen, spaces, e.g. `g/-john`, `g/john-`, `g/john doe` |
| `r/` | RSVP status | `yes`, `no`, `pending` | Any other value, e.g. `r/maybe` |
| `t/` | Tag | Alphanumeric tag, repeatable, e.g. `t/python t/ml` | Symbols/spaces, e.g. `t/machine-learning`, `t/data science` |
| `d/` | Event date | `YYYY-MM-DD`, e.g. `d/2026-10-03` | Invalid date format, e.g. `d/03-10-2026` |
| `l/` | Event location | Optional free text, e.g. `l/NUS COM1` |  |
| `desc/` | Event description | Optional free text, e.g. `desc/Weekly meetup` |  |
| `checkin/` | Check-in filter status | `yes`, `no` | Any other value, e.g. `checkin/maybe` |

For required fields, an empty prefix value is invalid unless explicitly stated otherwise.
Use the exact prefix expected by each command. Prefixes are not interchangeable.

---

## 4. Index and List Behavior

- Commands with `INDEX` use the index from the currently displayed list.
- If the list is filtered, the index refers to the filtered results, not the full list.
- After `search` or `filter`, always re-check the visible indexes before running `edit`, `delete`, `checkin`, `assign`, or `view`.

Example:
1. `filter r/yes`
2. `checkin 2`

The command applies to item `2` in the filtered list, not item `2` from an earlier unfiltered list.

---

## 5. Common Mistakes and Quick Fixes

- Missing required prefix (for example, no `e/` in `add`) -> include all required prefixes.
- Invalid index -> ensure index is a positive integer within the displayed list range.
- Wrong team prefix -> use `tm/` for `add` and `edit`, and `team/` for `assign` and `filter`.
- Invalid RSVP value -> use only `yes`, `no`, or `pending`.
- Multiple filter criteria in one command -> use exactly one filter criterion per `filter` command.
- Using command in wrong mode -> use event commands outside an event, and participant commands inside an event.

If a command fails with format errors, copy the exact `Format` block from the relevant command page and retry.

---

# Common Commands

This page describes commands that are available in both app modes.

---

## 1. Help Command

Used to open the help window and view usage instructions.

#### Format
`help`

#### Example Usage
```
help
```
![Command](images/help/command.png)

#### Successful Execution
Opens a new window containing the User Guide link.

![Result](images/help/result.png)

#### Notes
- Can be used in any mode.

---

## 2. List Command

Used to list all events or all participants depending on the current mode.

#### Format
`list`

#### Example Usage
Outside an event:

```
list
```

![Command outside an event](images/list/eventcommand.png)

Inside an event:

```
list
```

![Command inside an event](images/list/participantcommand.png)

#### Successful Execution
Outside an event: `Listed all events`

![Result outside an event](images/list/eventresult.png)

Inside an event: `Listed all participants`

![Result inside an event](images/list/participantresult.png)

#### Notes
- Works differently depending on the current mode.

---

## 3. Search Command

Used to search for matching events or participants depending on the current mode.

#### Format
`search [KEYWORD]...`

#### Example Usage
Outside an event:

```
search Tech
```

![Command outside an event](images/search/eventcommand.png)

Inside an event:

```
search [KEYWORD]...
```

![Command inside an event](images/search/participantcommand.png)

#### Successful Execution
Outside an event: matching events are shown in the event list.

![Result outside an event](images/search/eventresult.png)

Inside an event: matching participants are shown in the participant list.

![Result inside an event](images/search/participantresult.png)

#### Notes
- Can be used in any mode.
- The results depend on the current mode.
- Outside an event, `search` checks event name, date, location, and description.
- Inside an event, `search` checks participant name, phone, address, email, team, GitHub username, and check-in status.
- Matching is case-insensitive and works on substrings.

---

## 4. Switch Theme Command

Used to switch the application theme.

#### Format
`switchtheme [dark|light]`

#### Example Usage
```
switchtheme light
```

![Command](images/switchmode/command.png)

#### Successful Execution
`Switched to light mode.`

![Result](images/switchmode/result.png)

#### Notes
- Can be used in any mode.
- Only `dark` and `light` are valid values.

---

# Event Commands

This page describes commands that are primarily used while you are outside an event and managing the event list.

---

## 1. Event Creation and Setup

### 1.1 Add Event Command
<a id="cmd-addevent"></a>

Used to add an event to the event list by specifying the name, date, and optional details such as location and description.

#### Format
`addevent n/[EVENT NAME] d/[DATE] [l/LOCATION] [desc/DESCRIPTION]`

#### Example Usage
```
addevent n/Tech Meetup 2026 d/2026-06-15 l/NUS Techno Edge desc/Annual tech networking session
```
![Command](images/addevent/command.png)

#### Successful Execution
![Command](images/addevent/result.png)

#### Notes
- Can only be used outside an event.
- `NAME` must start with an alphanumeric character and can only contain alphanumeric characters and spaces. It must not be blank.
- `DATE` must follow the format `YYYY-MM-DD` e.g. `2026-06-15`.
- `LOCATION` and `DESCRIPTION` are optional.
- Duplicate events with the same name are not allowed.

---

## 2. Event Maintenance

### 2.1 Edit Event Command
<a id="cmd-editevent"></a>

Used to edit one or more selected details of an existing event in the event list.

#### Format
`editevent [INDEX] [n/EVENT NAME] [d/DATE] [l/LOCATION] [desc/DESCRIPTION]`

#### Example Usage
Edit multiple fields:

```
editevent 1 n/Hack Night d/2026-08-20 l/NUS COM1 desc/Bring your laptop
```

![Command](images/edit-event/command.png)

Edit only the location:

```
editevent 1 l/NUS COM2
```

Edit only the description:

```
editevent 1 desc/Updated event description
```

#### Successful Execution

![Result](images/edit-event/result.png)

#### Notes
- Can only be used outside an event.
- Index must be a positive integer.
- You only need to provide the field or fields you want to edit. Fields not specified in the command will remain unchanged.
- At least one field to edit must be provided.
- Location can be cleared with `l/` followed by no location text. Trailing spaces are ignored, so `l/ ` also clears the location.
- Description can be cleared with `desc/` followed by no description text. Trailing spaces are ignored, so `desc/ ` also clears the description.

### 2.2 Delete Event Command
<a id="cmd-deleteevent"></a>

Used to delete an event from the event list. The participant list stored under that event is deleted together with it.

#### Format
`deleteevent [INDEX]`

#### Example Usage
```
deleteevent 1
```

![Command](images/delete-event/command.png)

#### Successful Execution

![Result](images/delete-event/result.png)

#### Notes
- Can only be used outside an event.
- Index must be a positive integer.
- Use this command carefully because the event's participant list is also removed.

---

## 3. Event Navigation

### 3.1 Enter Event Command
<a id="cmd-enter-event"></a>

Used to enter an event and switch into participant-management mode for that event.

#### Format
`enter event [INDEX]`

#### Example Usage
```
enter event 1
```
![Command](images/enter-event/command.png)

#### Successful Execution

![Result](images/enter-event/result.png)

#### Notes
- Can only be used outside an event.
- Index must be a positive integer.
- You must leave the current event before entering another one.

---

## 4. Application Exit

### 4.1 Exit Command
<a id="cmd-exit"></a>

Used to close the application.

#### Format
`exit`

#### Example Usage
```
exit
```
![Command](images/exit/command.png)

#### Successful Execution
The application is exited.

#### Notes
- This command only succeeds outside an event.
- If you are currently inside an event, leave it first before exiting.

---

# Participant Commands

This page describes commands that are used while you are inside an event and managing that event's participants.

See [Command Fundamentals](#command-fundamentals) for command syntax, prefix rules, index behavior, and common input mistakes.

---

## 1. Participant Management

### 1.1 Add Command
<a id="cmd-add"></a>

Used to add a participant to the currently entered event.

#### Format
`add n/[NAME] p/[PHONE] e/[EMAIL] a/[ADDRESS] [tm/TEAM] [g/GITHUB_USERNAME] [r/RSVP_STATUS] [t/TAG]...`

#### Example Usage
```
add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 tm/Development g/johndoe r/yes t/friends
```
![Command](images/add/command.png)

#### Successful Execution
![Command](images/add/result.png)

#### Notes
- Can only be used inside an event.
- Name, phone, email, and address are required.
- `NAME` can contain alphanumeric characters (including accented characters e.g. José, Tomáš), spaces, apostrophes (`'`), hyphens (`-`), and forward slashes (`/`) e.g. `O'Brian`, `s/o Kumar`. Names cannot exceed 100 characters.
- `RSVP_STATUS` must be `yes`, `no`, or `pending` (case-insensitive). Defaults to `pending` if not provided.
- `TEAM` must be alphanumeric and at most 15 characters.
- Two participants are considered duplicates if they share the same name and either the same phone number or the same email. Duplicate participants cannot be added to the same event.

### 1.2 Edit Command
<a id="cmd-edit"></a>

Used to edit the details of an existing participant in the current event.

#### Format
`edit [INDEX] [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [g/GITHUB_USERNAME] [r/RSVP_STATUS] [tm/TEAM] [t/TAG]...`

#### Example Usage
```
edit 1 p/91234567 e/johndoe@example.com
```
![Command](images/edit/command.png)

#### Successful Execution
![Command](images/edit/result.png)

#### Notes
- Can only be used inside an event.
- Index must be a positive integer.
- At least one field to edit must be provided.
- Existing values will be overwritten by the new values.
- `NAME` follows the same constraints as the `add` command — alphanumeric characters (including accented), spaces, apostrophes, hyphens, and forward slashes. Cannot exceed 100 characters.
- `RSVP_STATUS` must be `yes`, `no`, or `pending` (case-insensitive).
- `TEAM` must be alphanumeric and at most 15 characters.
- Clear all tags by typing `t/` with nothing after it.
- Clear the team by typing `tm/` with nothing after it.
- Editing a participant to match another participant's name and phone or email will be rejected as a duplicate.

### 1.3 Delete Command
<a id="cmd-delete"></a>

Used to delete a participant from the current event.

#### Format
`delete [INDEX]`

#### Example Usage
```
delete 1
```
![Command](images/delete-applicants/delete-command.png)

#### Successful Execution
`Deleted Participant: ...`

![Result](images/delete-applicants/delete-result.png)

#### Notes
- Can only be used inside an event.
- Index must be a positive integer.

### 1.4 Clear Command
<a id="cmd-clear"></a>

Used to clear all participants from the current event.

#### Format
`clear`

#### Example Usage
```
clear
```
![Command](images/clear/clear-command.png)

#### Successful Execution
`Address book has been cleared!`

![Result](images/clear/clear-result.png)

#### Notes
- Can only be used inside an event.
- This removes all participants from the current event.

---

## 2. Team and Attendance Management

### 2.1 Assign Team Command
<a id="cmd-assign"></a>

Used to assign a participant to a team.

#### Format
`assign [INDEX] team/[TEAM NAME]`

#### Example Usage
```
assign 2 team/Alpha
```
![Command](images/assign-team/example.jpg)

#### Successful Execution
`Assigned [participant] to Team Alpha.`

![Result](images/assign-team/result.png)

#### Notes
- Can only be used inside an event.
- Index must be a positive integer.
- Team names must be alphanumeric and at most 15 characters.

### 2.2 Check-In Command
<a id="cmd-checkin"></a>

Used to mark a participant as checked in.

#### Format
`checkin [INDEX]`

#### Example Usage
```
checkin 3
```
![Command](images/checkin/command.png)

#### Successful Execution

![Result](images/checkin/result.png)

#### Notes
- Can only be used inside an event.
- Index must be a positive integer.

---

## 3. Search, Filtering, and Viewing

### 3.1 Filter Command
<a id="cmd-filter"></a>

Used to filter the participant list using one criterion at a time.

#### Format
<tabs>
<tab header="RSVP">

`filter r/[RSVP_STATUS]`

</tab>
<tab header="Tag">

`filter t/[TAG]`

</tab>
<tab header="Team">

`filter team/[TEAM NAME]`

</tab>
<tab header="Check-in">

`filter checkin/[yes|no]`

</tab>
</tabs>

#### Example Usage
<tabs>
<tab header="RSVP">

```
filter r/yes
```
![Command](images/filter/rsvp_command.png)

</tab>
<tab header="Tag">

```
filter t/python
```
![Command](images/filter/tag_command.png)

</tab>
<tab header="Team">

```
filter team/Alpha
```
![Command](images/filter/team_command.png)

</tab>
<tab header="Check-in">

```
filter checkin/yes
```
![Command](images/filter/checkin_command.png)

</tab>
</tabs>

#### Successful Execution
<tabs>
<tab header="RSVP">

![Result](images/filter/rsvp_output.png)

</tab>
<tab header="Tag">

![Result](images/filter/tag_output.png)

</tab>
<tab header="Team">

![Result](images/filter/team_output.png)

</tab>
<tab header="Check-in">

![Result](images/filter/checkin_output.png)

</tab>
</tabs>

#### Notes
- Can only be used inside an event.
- Supported prefixes are `r/`, `t/`, `team/`, and `checkin/`.
- Only one filter criterion can be used per command (e.g., `filter r/yes t/python` is invalid).
- Filtering is not cumulative across commands; each `filter` command replaces the previous filter/search.
- `checkin/` accepts `yes` or `no`(case-insensitive).

### 3.2 View Command
<a id="cmd-view"></a>

Used to show the details of a selected participant.

#### Format
`view [INDEX]`

#### Example Usage
```
view 1
```
![Command](images/view/command.png)

#### Successful Execution
![Result](images/view/output.png)


#### Notes
- This command can only be used inside an event.
- `INDEX` must be a positive integer.
- The `INDEX` must refer to a participant currently shown in the displayed list, including filtered or searched results.
- The command fails if the `INDEX` is invalid or out of range.

### 3.3 Statistics Command
<a id="cmd-statistics"></a>

Used to display the current event's participant statistics summary.

#### Format
`statistics`

#### Example Usage
```
statistics
```
![Command](images/statistics/command.png)

#### Successful Execution
![Result](images/statistics/output.png)

#### Notes
- Can only be used inside an event.
- This is a read-only command; it does not edit participant data.
- If you want to return to normal participant list operations, use commands like `list`, `filter`, `search`, etc.
- The command format is `statistics` only (no index or prefixes needed).

---

## 4. Import and Export

### 4.1 Import Command
<a id="cmd-import"></a>

Used to import participants from a CSV file into the current event.

#### Format
`import [FILE_PATH]`
`import list`

#### Example Usage
```text
import data/export/hacknight.csv
```
![Command](images/import-export/import-command.png)

To list discoverable CSV files:

```text
import list
```
![Result](images/import-export/import-found.png)

#### Successful Execution
Participants from the CSV file are imported into the current event. Invalid rows and duplicates are skipped and reported.

![Result](images/import-export/import-result.png)

#### Notes
- Can only be used inside an event.
- Only `.csv` files are supported.
- `import list` shows discoverable CSV files.
- Required CSV headers are `name`, `phone`, `email`, and `address`.

### 4.2 Export Command
<a id="cmd-export"></a>

Used to export participants from the current event to a CSV file.

#### Format
`export [FILE_PATH]`

#### Example Usage
```text
export data/ForTestOnly.csv
```
![Command](images/import-export/export-command.png)

To export using the default path:

```text
export
```
![Command](images/import-export/export-default-command.png)

#### Successful Execution
`Exported ... participant(s) to ...`

![Result](images/import-export/export-result.png)

Default-path export result:

![Result](images/import-export/export-default-result.png)

#### Notes
- Can only be used inside an event.
- Only `.csv` files are supported.
- If no file path is provided, the default export path is used.
- If the target file already exists, the app exports to a timestamped file instead.

---

## 5. Event Navigation

### 5.1 Leave Event Command
<a id="cmd-leave-event"></a>

Used to leave the current event and return to the event list.

#### Format
`leave event`

#### Example Usage
```
leave event
```
![Command](images/leave-event/command.png)

#### Successful Execution

![Result](images/leave-event/result.png)

#### Notes
- Can only be used inside an event.
- Ensure that there is no space after `event`.

