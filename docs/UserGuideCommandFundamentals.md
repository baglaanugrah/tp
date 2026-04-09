---
layout: default.md
title: "User Guide - Command Fundamentals"
pageNav: 3
---

# Command Fundamentals

This page is the shared conventions reference for how commands are written and interpreted across TeamEventPro.
Read this once before using [Event Commands](UserGuideEvents.md) or [Participant Commands](UserGuideParticipants.md).

---

## 1. Command notation

- Words in `UPPER_CASE` are parameters to be supplied by the user.
- Items followed by `...` can be used multiple times.
- For prefixed arguments, parameter order usually does not matter unless stated otherwise.
- Indexes refer to the numbers shown in the displayed list.
- Dates should follow the format `YYYY-MM-DD`.

---

## 2. Command structure and modes

TeamEventPro operates in two modes:
- **Outside an event**: event-level commands such as `addevent`, `editevent`, `deleteevent`, `enter event`.
- **Inside an event**: participant-level commands such as `add`, `edit`, `assign`, `filter`, `checkin`.

Most commands follow one of these patterns:
- `COMMAND [INDEX] [PREFIX/VALUE]...`
- `COMMAND KEYWORD INDEX` (example: `enter event 1`)
- `COMMAND` (example: `list`, `help`, `statistics`)

---

## 3. Prefix reference

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

## 4. Index and list behavior

- Commands with `INDEX` use the index from the currently displayed list.
- If the list is filtered, the index refers to the filtered results, not the full list.
- After `search` or `filter`, always re-check the visible indexes before running `edit`, `delete`, `checkin`, `assign`, or `view`.

Example:
1. `filter r/yes`
2. `checkin 2`

The command applies to item `2` in the filtered list, not item `2` from an earlier unfiltered list.

---

## 5. Common mistakes and quick fixes

- Missing required prefix (for example, no `e/` in `add`) -> include all required prefixes.
- Invalid index -> ensure index is a positive integer within the displayed list range.
- Wrong team prefix -> use `tm/` for `add` and `edit`, and `team/` for `assign` and `filter`.
- Invalid RSVP value -> use only `yes`, `no`, or `pending`.
- Multiple filter criteria in one command -> use exactly one filter criterion per `filter` command.
- Using command in wrong mode -> use event commands outside an event, and participant commands inside an event.

If a command fails with format errors, copy the exact `Format` block from the relevant command page and retry.

---

## 6. Navigation

- [Back to Introduction, Modes, and Common Commands](UG.md)
- [Back to Common Commands](UserGuideCommonCommands.md)
- [Go to Event Commands](UserGuideEvents.md)
- [Go to Participant Commands](UserGuideParticipants.md)
