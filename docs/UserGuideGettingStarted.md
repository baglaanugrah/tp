---
layout: default.md
title: "User Guide - Getting Started"
pageNav: 3
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

## 4. First 5-minute workflow

Try this sequence to confirm your app is working end-to-end:

1. `addevent n/Tech Meetup d/2026-06-15 l/NUS COM1 desc/Networking night`
2. `enter event 1`
3. `add n/John Doe p/98765432 e/johnd@example.com a/311 Clementi Ave 2`
4. `checkin 1`
5. `statistics`
6. `leave`

If all commands execute successfully, your setup is complete.

---

## 5. Where to go next

- For shared command conventions, prefix usage, and index/list behavior, see [Command Fundamentals](UserGuideCommandFundamentals.md).
- View global commands in [Common Commands](UserGuideCommonCommands.md).
- View mode-specific command details in [Event Commands](UserGuideEvents.md) and
  [Participant Commands](UserGuideParticipants.md).

---

## 6. Navigation

- [Back to Introduction and App Modes](UG.md)
- [Go to Command Fundamentals](UserGuideCommandFundamentals.md)
- [Go to Common Commands](UserGuideCommonCommands.md)
- [Go to Event Commands](UserGuideEvents.md)
- [Go to Participant Commands](UserGuideParticipants.md)
