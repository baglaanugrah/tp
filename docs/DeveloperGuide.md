---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# AB-3 Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

The sequence diagram below illustrates the interactions for assigning a participant to a team using `execute("assign 2 team/Alpha")`.

<puml src="diagrams/AssignTeamSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `assign 2 team/Alpha` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Filter participants

#### Overview

The `filter` command narrows the currently displayed participant list using one criterion per command.

Supported criteria:
* RSVP: `r/yes`, `r/no`, `r/pending`.
* Tag: `t/<tag>`.
* Team: `team/<teamName>`.
* Check-in: `checkin/yes` or `checkin/no`.

Filtering is valid only in event participant mode. If the user is not inside an event, execution fails with `Please enter an event first.` (`Messages.MESSAGE_ENTER_EVENT_FIRST`).

#### Architecture (key components)

The filter feature is implemented across three main areas:
* **Parser layer (`FilterCommandParser`)**: validates command format and constructs `PersonMatchesFilterPredicate`.
* **Command layer (`FilterCommand`)**: checks event context and applies the predicate via `Model#updateFilteredPersonList(...)`.
* **Model layer (`PersonMatchesFilterPredicate` + `Model`)**: evaluates each `Person` and updates the filtered observable participant list exposed to the UI.

<puml src="diagrams/FilterFeatureClassDiagram.puml" width="500" />

#### Implementation details

1. `AddressBookParser` dispatches `filter ...` input to `FilterCommandParser`.
2. `FilterCommandParser` tokenizes the arguments and validates that:
   * input is not empty;
   * exactly one supported filter prefix is provided (`r/`, `t/`, `team/`, or `checkin/`);
   * unsupported participant prefixes in the same command (e.g., `n/`, `e/`) are rejected;
   * duplicate filter prefixes are rejected.
3. The parser builds a `PersonMatchesFilterPredicate` and returns a `FilterCommand`.
4. During execution, `FilterCommand` first checks `model.isInEventParticipantsMode()`.
5. If the check passes, it calls `model.updateFilteredPersonList(predicate)`.
6. The command then returns a `CommandResult` using the size of `model.getFilteredPersonList()` for feedback.

Failure paths:
* Invalid filter format/value -> `ParseException` (shows command usage or value constraints).
* Valid parse but not in event participant mode -> `CommandException` with `Messages.MESSAGE_ENTER_EVENT_FIRST`.

<puml src="diagrams/FilterCommandSequenceDiagram.puml" width="720" alt="Sequence diagram for filter command" />

#### Design considerations

**Aspect: Single-criterion filter input**
* **Current choice:** accept only one filter criterion per command.
  * Pros: simpler parsing, clearer error handling, predictable behaviour.
  * Cons: users cannot compose criteria in one command (e.g., tag + RSVP together).

**Aspect: Event-context restriction**
* **Current choice:** allow filtering only in event participant mode.
  * Pros: prevents ambiguity about whether filtering targets events or participants.
  * Cons: users must explicitly enter an event before filtering participants.

**Aspect: Predicate evaluation strategy**
* **Current choice:** `PersonMatchesFilterPredicate` supports all four fields internally, while parser currently activates one criterion per command.
  * Pros: reusable predicate type with a single execution path in `FilterCommand`.
  * Cons: extra predicate flexibility is not exposed to users under the current single-criterion command syntax.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

### Anugrah Bagla


### Zhou Jinhao


### Debopam Roy


### Han Shangda


### Manya Agarwal

Used Cursor (auto mode) for:
* Designing and refining UI changes across the application.
* Understanding the codebase architecture while implementing `statistics`, `filter`, and `view` commands.
* Writing and improving manual test cases for `statistics`, `filter`, and `view` commands.
* Debugging issues and performing error checking.
* Reviewing code and documentation quality.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* Tech meetup organizers managing small-to-medium events (20–150 participants) in Singapore
* Manages multiple events per month with varying participant lists
* Needs to perform live check-ins and track real-time attendance during events
* Prefers desktop applications for stability and offline capability during events
* Types fast and prefers keyboard shortcuts over mouse navigation
* Comfortable with command-line interfaces and text-based input
* Requires instant filtering and searching capabilities (by RSVP, attendance, team, dietary needs)
* Manages hackathon team assignments and participant skill tracking
* Values data accuracy and quick error correction during live events

**Value proposition**:

Enable tech event organizers to manage participants. Optimized for live event scenarios with keyboard-first commands for rapid check-ins, instant search, real-time filtering, and on-the-spot team assignments—all without requiring an internet connection.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                                                          | So that I can…​                                                    |
|----------|-----------------------------------------------|------------------------------------------------------------------------|--------------------------------------------------------------------|
| `* * *`  | new user                                      | see usage instructions and command examples                            | learn the app quickly and refer back when needed                   |
| `* * *`  | meetup organizer                              | add a participant with name, contact, GitHub username, and RSVP status | build my participant list rapidly before events                    |
| `* * *`  | meetup organizer                              | edit participant details using quick commands                          | fix typos or update information instantly during registration      |
| `* * *`  | meetup organizer                              | delete a participant                                                   | remove cancellations, duplicates, or test entries                  |
| `* * *`  | meetup organizer                              | search participants by name, email, or GitHub username                 | locate specific attendees in seconds during events                 |
| `* * *`  | entrance desk organizer                       | mark a participant as checked-in with one command                      | process arrivals quickly without slowing the entry queue           |
| `* * *`  | meetup organizer                              | list all participants with a single command                            | get a complete overview after filtering or searching               |
| `* * *`  | meetup organizer                              | undo my last action                                                    | quickly recover from accidental deletions or edits                 |
| `* *`    | hackathon organizer                           | assign participants to teams                                           | organize team-based events efficiently                             |
| `* *`    | meetup organizer                              | filter participants by RSVP status (Yes/No/Pending)                    | know exactly who's confirmed and follow up with pending responses  |
| `* *`    | meetup organizer                              | filter participants by check-in status                                 | identify no-shows and track actual attendance vs RSVPs             |
| `* *`    | hackathon organizer                           | filter participants by team assignment                                 | view and manage team rosters quickly                               |
| `* *`    | meetup organizer with catering responsibilities | filter participants by dietary requirements                          | ensure accurate meal planning and allergen management              |
| `* *`    | meetup organizer                              | tag participants with custom labels                                    | categorize attendees (e.g., speaker, volunteer, VIP)               |
| `* *`    | frequent organizer                            | import participant lists from CSV files                                | migrate data from previous tools quickly                           |
| `* *`    | frequent organizer                            | export participant data to CSV                                         | share reports with co-organizers or sponsors                       |
| `*`      | meetup organizer                              | view attendance statistics and RSVP conversion rates                   | analyze event turnout and improve future planning                  |
| `*`      | hackathon organizer                           | automatically balance teams by declared skills                         | create fair teams without manual sorting                           |
| `*`      | meetup organizer                              | see a timeline of recent actions                                       | track what changes were made during busy check-in periods          |
| `*`      | meetup organizer with accessibility needs     | use keyboard shortcuts for all operations                              | manage events efficiently without relying on mouse input           |

*{More to be added}*

### Use cases

#### Use case: UC01 - Enter an event

```
System: TeamEventPro
Actor: Organizer
MSS:
  1. Organizer requests to enter an event.
  2. TeamEventPro requests the event index.
  3. Organizer provides a valid event index.
  4. TeamEventPro switches to that event's participant view and displays the participant list.
  Use case ends.

Extensions:
  3a. The index is invalid.
      3a1. TeamEventPro shows an invalid index error.
      3a2. Organizer provides another index.
      Steps 3a1-3a2 are repeated until a valid index is provided.
      Use case resumes from step 4.

  1a. Organizer is already in an event participant view.
      1a1. TeamEventPro shows an error asking the organizer to leave the current event first.
      Use case ends.
```

#### Use case: UC02 - Add a participant

```
System: TeamEventPro
Actor: Organizer
Preconditions: Organizer is in an event participant view.
MSS:
  1. Organizer requests to add a participant.
  2. TeamEventPro requests the participant details.
  3. Organizer enters valid participant details.
  4. TeamEventPro adds the participant and shows a success message.
  Use case ends.

Extensions:
  3a. The entered details are invalid.
      3a1. TeamEventPro shows the relevant format constraints.
      3a2. Organizer enters corrected details.
      Steps 3a1-3a2 are repeated until all details are valid.
      Use case resumes from step 4.

  3b. A participant with the same identity already exists.
      3b1. TeamEventPro shows a duplicate participant error.
      Use case ends.
```

#### Use case: UC03 - Filter participants

```
System: TeamEventPro
Actor: Organizer
Preconditions: Organizer is in an event participant view.
MSS:
  1. Organizer requests to filter participants.
  2. TeamEventPro requests one filter criterion.
  3. Organizer provides one valid criterion (RSVP, tag, team, or check-in).
  4. TeamEventPro displays the filtered participant list and count.
  Use case ends.

Extensions:
  3a. The filter format or value is invalid.
      3a1. TeamEventPro shows the filter usage and/or constraints.
      3a2. Organizer enters a corrected filter command.
      Use case resumes from step 4.

  3b. More than one criterion is provided in one command.
      3b1. TeamEventPro rejects the command and shows correct usage.
      Use case ends.
```

#### Use case: UC04 - Check in a participant

```
System: TeamEventPro
Actor: Organizer
Preconditions: Organizer is in an event participant view.
MSS:
  1. Organizer requests to check in a participant.
  2. TeamEventPro requests the participant index.
  3. Organizer provides a valid participant index.
  4. TeamEventPro marks the participant as checked in and shows a success message.
  Use case ends.

Extensions:
  3a. The index is invalid.
      3a1. TeamEventPro shows an invalid index error.
      3a2. Organizer provides another index.
      Steps 3a1-3a2 are repeated until a valid index is provided.
      Use case resumes from step 4.
```

#### Use case: UC05 - View event statistics

```
System: TeamEventPro
Actor: Organizer
Preconditions: Organizer is in an event participant view.
MSS:
  1. Organizer requests to view event statistics.
  2. TeamEventPro computes participant statistics for the current event.
  3. TeamEventPro displays the statistics dashboard.
  Use case ends.

Extensions:
  2a. The event has no participant data for one or more categories.
      2a1. TeamEventPro shows empty-state output for those categories.
      Use case resumes from step 3.
```

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Any data entered should be saved locally to a human-readable text file automatically after every valid command execution.
5.  The system should provide clear, user-friendly error messages when invalid command formats are entered instead of terminating or crashing.
6.  The system should respond to any search or filtering command within 500 milliseconds even when the database is at maximum capacity (1000 persons).
7.  The software should be delivered as a single JAR file that does not require an installer.
8.  The system is only required to support 8-digit Singaporean phone numbers and standard international email formats for participant contact details.


### Glossary

* **Mainstream OS**: Common desktop operating systems that typical users run applications on, such as Windows, macOS, and Linux.
* **Organizer**: The primary user of TeamEventPro who manages events and their participants.
* **Event**: A scheduled activity managed in TeamEventPro, identified by details such as its name, date, location, and description.
* **Global Event View**: The top-level context in which the organizer browses and manages events before entering a specific event.
* **Event Participant View**: The in-event context shown after the organizer enters an event, where commands operate on that event's participants.
* **Participant**: A person registered under a specific event in TeamEventPro.
* **Participant Record**: The full set of data stored for a participant, such as contact details, tags, team assignment, RSVP status, and check-in status.
* **RSVP**: A participant's attendance response, recorded as `yes`, `no`, or `pending`.
* **Check-in Status**: Whether a participant has been marked as present at the event.
* **Prefix**: A field marker used in command input to indicate how a value should be interpreted by the parser (e.g. `n/` for name).
* **Duplicate Participant**: A participant entry that the system considers identical to an existing participant according to its duplicate-detection rules.
* **CSV (Comma-Separated Values)**: A plain-text file format used to store tabular data, where each line represents a row and commas separate values.
--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample events. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a participant

1. Deleting a participant while all participants are being shown

   1. Prerequisites: Enter an event so the participant list is shown. Multiple participants in the current event list.

   1. Test case: `delete 1`<br>
      Expected: The first participant is deleted from the list. Details of the deleted participant are shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No participant is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Filtering participants

1. Filtering while inside an event

   1. Prerequisites: Launch the app, select an event with `enter event INDEX` so the participant list is shown. Ensure the list has a mix of RSVP values, tags, team assignments, and check-in states (use sample data or add/edit participants as needed).

   1. Test case: `filter r/yes`<br>
      Expected: Only participants whose RSVP is “yes” remain visible; the status message shows how many persons are listed; list updates immediately.

   1. Test case: `filter t/` followed by a tag that at least one participant has (e.g. `filter t/python` if such a tag exists).<br>
      Expected: Only participants with that tag are shown; count in the message matches the visible list.

   1. Test case: `filter team/TEAMNAME` where `TEAMNAME` matches an assigned team.<br>
      Expected: Only participants on that team are shown.

   1. Test case: `filter checkin/yes` or `filter checkin/no` (values as supported by the app).<br>
      Expected: List narrows to checked-in or not checked-in participants accordingly.

   1. Test case: `list` after a successful filter.<br>
      Expected: Full participant list for the current event is shown again (filter predicate cleared as per normal `list` behavior).

1. Invalid filter input

   1. Prerequisites: Same as above—must be inside an event with participants listed.

   1. Test case: `filter` with no arguments, or with two criteria in one command (e.g. `filter r/yes t/python`).<br>
      Expected: No change to the filtered list (or error message); user sees invalid command / usage feedback.

   1. Test case: `filter r/invalid` (or other malformed RSVP value).<br>
      Expected: Parse error; list unchanged.

1. Filtering without entering an event first

   1. Prerequisites: Launch the app but do **not** run `enter event`; stay in the global event view.

   1. Test case: `filter r/yes`<br>
      Expected: Command fails with a message indicating the user must enter an event first; participant list not updated.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
