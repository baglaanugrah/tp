---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# TeamEventPro Developer Guide

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

The UI consists of a `MainWindow` that is made up of parts e.g. `CommandBox`, `ResultDisplay`, `PersonListPanel`, `EventListPanel`, `StatisticsPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2526S2-CS2103T-W11-1/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2526S2-CS2103T-W11-1/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps references to the `Logic` component in both `UiManager` and `MainWindow`, because the `UI` relies on `Logic` to execute commands and refresh state-dependent views.
* depends on classes in the `Model` component, as it displays `Person` and `Event` objects and renders participant details.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

The sequence diagram below illustrates the interactions for assigning a participant to a team using `execute("assign 2 team/Alpha")`.

<puml src="diagrams/AssignTeamSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `assign 2 team/Alpha` Command" />

The class diagram below summarizes the main classes involved in parsing and executing the `assign` command.

<puml src="diagrams/AssignTeamClassDiagram.puml" alt="Main classes involved in the assign command" width="700" />

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

* stores participant data in an `AddressBook` (all `Person` objects in a `UniquePersonList`).
* stores event data in an `EventBook` (all `Event` objects in a `UniqueEventList`).
* stores filtered views as unmodifiable observable lists for both participants (`ObservableList<Person>`) and events (`ObservableList<Event>`), so the UI updates automatically when filters or underlying data change.
* tracks the currently active event context (`activeEvent`) when the app is in event-participants mode.
* stores a `UserPrefs` object that represents user preferences, exposed externally through `ReadOnlyUserPrefs`.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save address book data, event book data, and user preference data in JSON format, and read them back into corresponding objects.
* inherits from `AddressBookStorage`, `EventBookStorage`, and `UserPrefsStorage`, which means it can be treated as any one of them when only one storage responsibility is needed.
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Add Event Feature

#### Implementation

The add event feature is facilitated by `AddEventCommand`. It allows the user to create a new event with a name, date, and optionally a location and description.

The command follows these steps when executed:

1. `AddressBookParser` receives the input and creates an `AddEventCommandParser`.
2. `AddEventCommandParser` tokenises the input using `ArgumentTokenizer` with prefixes `n/`, `d/`, `l/`, and `desc/`, then constructs an `AddEventCommand` containing the new `Event` object.
3. `AddEventCommand#execute()` checks that the app is not in event participant mode. If it is, a `CommandException` is thrown.
4. `AddEventCommand#execute()` checks for duplicates via `Model#hasEvent()`. Two events are considered duplicates if they share the same name. If a duplicate is found, a `CommandException` is thrown.
5. `Model#addEvent()` is called, adding the event to the `EventBook`. The `EventBook` is then persisted to `data/eventbook.json` via `JsonEventBookStorage`. The filtered event list is then reset via `Model#updateFilteredEventList()` to ensure the UI reflects the full updated event list regardless of any prior search or filter.
6. A `CommandResult` with the success message is returned.

#### Design Considerations

**Aspect: What constitutes a duplicate event:**

* **Current choice:** Two events are duplicates if they share the same name (case-sensitive).
    * Pros: Simple and predictable.
    * Cons: Does not catch near-duplicates such as `Tech Meetup` and `tech meetup`.

* **Alternative:** Case-insensitive name comparison.
    * Pros: More robust duplicate detection.
    * Cons: Slightly more complex comparison logic.

---

### Add Participant Feature

#### Implementation

The add participant feature is facilitated by `AddCommand`. It allows the user to add a participant to the currently active event's participant list.

The command follows these steps when executed:

1. `AddressBookParser` receives the input and creates an `AddCommandParser`.
2. `AddCommandParser` tokenises the input using `ArgumentTokenizer` and constructs a `Person` object from the parsed fields. Required fields are `n/`, `p/`, `e/`, and `a/`. Optional fields are `team/`, `g/`, `r/`, and `t/`.
3. `AddCommand#execute()` checks that the app is in event participant mode. If not, a `CommandException` is thrown.
4. `AddCommand#execute()` checks for duplicates via `Model#hasPerson()`. Duplicate detection is handled by `Person#isSamePerson()`, which returns true if two persons share the same name (case-insensitive) **and** either the same phone number or the same email address.
5. `Model#addPerson()` is called, adding the participant to the active event's `AddressBook`.
6. A `CommandResult` with the formatted success message is returned using `Messages#format()`.

Notable field constraints enforced at the model level:

* `Name`: Must start with an alphanumeric character. Can contain alphanumeric characters (including Unicode letters for accented names), spaces, apostrophes, hyphens, and forward slashes. Maximum 100 characters.
* `Email`: Must match `Email#VALIDATION_REGEX` (`local-part@domain` with the rules documented in `Email#MESSAGE_CONSTRAINTS`). The entire string must be at most `Email#MAX_LENGTH` (64) characters inclusive.
* `Address`: Must match `Address#VALIDATION_REGEX` (non-blank; first character must not be whitespace). The entire string must be at most `Address#MAX_LENGTH` (100) characters inclusive.
* `Phone`: Must contain only digits, between 3 and 17 digits long.
* `RsvpStatus`: Must be `yes`, `no`, or `pending`. Defaults to `pending` if not provided.
* `Team`: Must be alphanumeric and at most 15 characters.

The class diagram below shows the `Person` model and all its associated fields:

<puml src="diagrams/AddCommandPersonDiagram.puml" alt="Person Class Diagram" />

The sequence diagram below illustrates the interactions within the `Logic` component when the user executes `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2`:

<puml src="diagrams/AddCommandSequenceDiagram.puml" alt="Add Command Sequence Diagram" />

#### Design Considerations

**Aspect: How duplicate participants are identified:**

* **Current choice:** Same name and same phone, or same name and same email.
    * Pros: Allows two people with the same name but genuinely different contact details to coexist in the same event.
    * Cons: Two entries for the same real person with slightly different names would not be caught.

* **Alternative 1 (original AB3 behaviour):** Name-only comparison.
    * Pros: Simple.
    * Cons: Too restrictive — common names like `John Tan` would block legitimate separate participants.

* **Alternative 2:** Phone or email alone (without name).
    * Pros: More reliable since phone numbers and emails are globally unique.
    * Cons: Would reject a shared office phone number used by two different participants.

---

### Check-In Feature

#### Implementation

The check-in feature is facilitated by `CheckInCommand`. It allows the user to mark a participant in the currently active event as checked in.

The command follows these steps when executed:

1. `AddressBookParser` receives the input and creates a `CheckInCommandParser`.
2. `CheckInCommandParser` tokenises the input and parses the preamble into an `Index`.
3. `CheckInCommand#execute()` checks that the app is currently in event participant mode. If not, a `CommandException` is thrown.
4. The target `Person` is retrieved from the filtered participant list using the parsed index. If the index is out of bounds, a `CommandException` is thrown.
5. `Model#checkInPerson()` is called to update the participant's attendance status in the active event.
6. A `CommandResult` with the formatted success message is returned using `Messages#format()`.

The class diagram below summarizes the main classes involved in parsing and executing the `checkin` command.

<puml src="diagrams/CheckInCommandClassDiagram.puml" alt="Main classes involved in the check-in command" width="650" />

The sequence diagram below illustrates the interactions within the `Logic` component when the user executes `checkin 1`.

<puml src="diagrams/CheckInCommandSequenceDiagram.puml" alt="Sequence diagram for the check-in command" width="720" />

#### Design Considerations

**Aspect: How the target participant is identified:**

* **Current choice:** Use the participant index from the currently displayed list.
    * Pros: Consistent with other participant-level commands such as `edit`, `delete`, and `assign`.
    * Cons: Users must be careful that filtered lists can change the visible index ordering.

* **Alternative:** Check in a participant by a unique field such as email.
    * Pros: Avoids index ambiguity after filtering or reordering.
    * Cons: More typing and less consistent with the rest of the participant command set.

**Aspect: Where the attendance update happens:**

* **Current choice:** Delegate the actual status update to `Model#checkInPerson()`.
    * Pros: Keeps `CheckInCommand` focused on command orchestration and validation.
    * Cons: Requires readers to follow the call into the model layer to see the final state update.

---

### Edit Participant Feature

#### Implementation

The edit participant feature is facilitated by `EditCommand`. It allows the user to update one or more fields of an existing participant in the currently active event.

The command follows these steps when executed:

1. `AddressBookParser` receives the input and creates an `EditCommandParser`.
2. `EditCommandParser` tokenises the input and builds an `EditPersonDescriptor` containing only the fields the user specified. At least one field must be present, otherwise a `ParseException` is thrown.
3. `EditCommand#execute()` checks that the app is in event participant mode. If not, a `CommandException` is thrown.
4. The target `Person` is retrieved from the filtered person list using the provided index. If the index is out of bounds, a `CommandException` is thrown.
5. A new `Person` object is constructed by combining the existing person's fields with the updated fields from `EditPersonDescriptor`.
6. `EditCommand#execute()` checks that the edited person does not conflict with any other existing participant in the full address book (not just the currently filtered list) via `Person#isSamePerson()`. Name comparison is case-insensitive. This prevents a duplicate from being introduced by editing fields one at a time across separate commands.
7. `Model#setPerson()` replaces the old participant with the edited one in the active event's `AddressBook`.
8. A `CommandResult` with the formatted success message is returned using `Messages#format()`.

Notable behaviours:

* Editing tags replaces all existing tags entirely. To clear all tags, use `t/` with no value.
* Editing team replaces the existing team. To clear the team, use `team/` with no value.
* All other field constraints follow the same validation rules as `AddCommand`.

#### Design Considerations

**Aspect: How edited fields are handled:**

* **Current choice:** `EditPersonDescriptor` holds only the fields the user specified. Unspecified fields retain their original values.
    * Pros: Users can update a single field without re-entering all other details.
    * Cons: Tags and team behave differently from other fields — they replace rather than append, which may be unintuitive.

* **Alternative:** Cumulative tag editing (append rather than replace).
    * Pros: More intuitive for users who want to add a tag without removing existing ones.
    * Cons: Harder to remove specific tags; requires a separate remove-tag command.

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

### Search Feature

#### Implementation

The search feature is facilitated by `SearchCommand`. It supports dual behaviour based on the current app context:

* In the global event view, it filters the event list using `EventMatchesKeywordsPredicate`.
* In event participant mode, it filters the participant list using `NameContainsKeywordsPredicate`.

The command follows these steps when executed:

1. `AddressBookParser` receives the input and creates a `SearchCommandParser`.
2. `SearchCommandParser` trims and validates the arguments. If the user provides no keyword, a `ParseException` is thrown.
3. `SearchCommandParser` splits the input into keywords and constructs a `SearchCommand`.
4. `SearchCommand#execute()` checks `model.isInEventParticipantsMode()`.
5. If the app is in event participant mode, `SearchCommand` applies `NameContainsKeywordsPredicate` through `Model#updateFilteredPersonList(...)`.
6. Otherwise, it applies `EventMatchesKeywordsPredicate` through `Model#updateFilteredEventList(...)`.
7. A `CommandResult` is returned using the size of the currently filtered list for user feedback.

<puml src="diagrams/SearchCommandSequenceDiagram.puml" width="760" alt="Sequence diagram for search command" />

#### Design considerations

**Aspect: Reusing one command word across two contexts**
* **Current choice:** `search` changes target based on whether the user is currently inside an event.
  * Pros: keeps the command set small and consistent across the app.
  * Cons: requires users to understand the current app context before predicting the result.

**Aspect: Predicate choice**
* **Current choice:** event search and participant search use separate predicate classes.
  * Pros: keeps matching logic close to the corresponding domain model.
  * Cons: shared matching behaviour, such as case-insensitive substring matching, is duplicated across predicate implementations.

### List Feature

#### Implementation

The list feature is facilitated by `ListCommand`. Like `search`, it is context-aware:

* In the global event view, it resets the event list to show all events.
* In event participant mode, it resets the participant list to show all participants in the current event.

The command follows these steps when executed:

1. `AddressBookParser` validates that `list` is used without extra arguments and constructs a `ListCommand` directly.
2. `ListCommand#execute()` checks `model.isInEventParticipantsMode()`.
3. If the app is in event participant mode, `ListCommand` calls `Model#updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS)`.
4. Otherwise, it calls `Model#updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS)`.
5. A `CommandResult` with the appropriate success message is returned.

<puml src="diagrams/ListCommandSequenceDiagram.puml" width="720" alt="Sequence diagram for list command" />

#### Design considerations

**Aspect: Resetting filtered views**
* **Current choice:** `list` always restores the full list for the current context instead of preserving previous filters.
  * Pros: provides a quick and predictable way to clear `search` results and return to the full list.
  * Cons: users cannot recover a previous filtered state after issuing `list`.

**Aspect: Using one command for both events and participants**
* **Current choice:** the same `list` command is used in both app contexts.
  * Pros: reduces command memorisation and aligns with the app's event-first workflow.
  * Cons: the success message and visible result depend on context, which may surprise first-time users.

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
Used Cursor and Codex for 
* Understanding the codebase to implement features.
* Design multiple features such as `enter event`, `checkin` etc.
* Writing test cases for the features.
* Debugging errors.

### Zhou Jinhao

Used Cursor (auto mode) for:
* Implementing the `assign-team` feature.
* Implementing `import` and `export` features.
* Implementing the real-time search feature.
* Debugging issues and performing error checking.
* Reviewing code and documentation quality.


### Debopam Roy
Used Claude (Claude Code) for:
* Understanding the codebase architecture while implementing the addevent, add participant, and edit participant commands.
* Designing and refining the duplicate participant detection logic (Person#isSamePerson()).
* Debugging issues and performing error checking, including Checkstyle violations and failing unit tests.
* Reviewing code and documentation quality, including alpha testing bugs and peer PR code reviews.

### Han Shangda

Used codex/cursor to support selected development and documentation tasks, including:
* refining the User Guide and Developer Guide wording and structure.
* checking the behavior of commands such as `list`, `search`, `switchtheme`, `editevent`, and `deleteevent`.
* identifying possible edge cases, error-message issues, and documentation inconsistencies.
* assisting with test checking, bug investigation, and troubleshooting during implementation.
All AI-assisted output was reviewed, edited, and verified before being included in the final submission.

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

* Tech meetup organizers managing small-to-medium events (20–150 participants).
* Manages multiple events per month with varying participant lists.
* Needs to perform live check-ins and track real-time attendance during events.
* Prefers desktop applications for stability and offline capability during events.
* Types fast and prefers keyboard shortcuts over mouse navigation.
* Requires instant filtering and searching capabilities (by RSVP, attendance, team, dietary needs).
* Manages hackathon team assignments and participant skill tracking.
* Values data accuracy and quick error correction during live events.

**Value proposition**:

Enable tech event organizers to manage participants. Optimized for live event scenarios with keyboard-first commands for rapid check-ins, instant search, real-time filtering, and on-the-spot team assignments—all without requiring an internet connection.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                                                          | So that I can…​                                                    |
|----------|-----------------------------------------------|------------------------------------------------------------------------|--------------------------------------------------------------------|
| `* * *`  | new user                                      | see usage instructions and command examples                            | learn the app quickly and refer back when needed                   |
| `* * *`  | meetup organizer                              | add events                                                             | create event records quickly during planning                       |
| `* * *`  | meetup organizer                              | edit event details                                                     | keep event information accurate when plans change                  |
| `* * *`  | meetup organizer                              | delete events                                                          | remove canceled or duplicate events cleanly                        |
| `* * *`  | meetup organizer                              | enter a selected event and return to the event list                    | switch quickly between event-level and participant-level workflows |
| `* * *`  | meetup organizer                              | add a participant with name, contact, GitHub username, and RSVP status | build my participant list rapidly before events                    |
| `* * *`  | meetup organizer                              | edit participant details using quick commands                          | fix typos or update information instantly during registration      |
| `* * *`  | meetup organizer                              | delete a participant                                                   | remove cancellations, duplicates, or test entries                  |
| `* * *`  | meetup organizer                              | search participants by name, email, or GitHub username                 | locate specific attendees in seconds during events                 |
| `* * *`  | entrance desk organizer                       | mark a participant as checked-in with one command                      | process arrivals quickly without slowing the entry queue           |
| `* * *`  | meetup organizer                              | list all participants with a single command                            | get a complete overview after filtering or searching               |
| `* * *`  | meetup organizer                              | exit the app from the command line                                     | close the tool quickly without leaving the keyboard                |
| `* *`    | hackathon organizer                           | assign participants to teams                                           | organize team-based events efficiently                             |
| `* *`    | meetup organizer                              | filter participants by RSVP status (Yes/No/Pending)                    | know exactly who's confirmed and follow up with pending responses  |
| `* *`    | meetup organizer                              | tag participants with custom labels                                    | categorize attendees (e.g., speaker, volunteer, VIP)               |
| `* *`    | frequent organizer                            | import participant lists from CSV files                                | migrate data from previous tools quickly                           |
| `* *`    | frequent organizer                            | export participant data to CSV                                         | share reports with co-organizers or sponsors                       |
| `*`      | meetup organizer                              | clear all participants for the current event                           | reset quickly when preparing a fresh test dataset                  |

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

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 participants across all events and up to 100 events without any command taking more than 1 second to execute under normal usage.
3. The application should launch and be ready for use within 5 seconds on any computer meeting the minimum Java `17` requirement.
4. All `search` and `filter` commands should return results within 500 milliseconds even when the participant list contains 1000 entries.
5. All data should be saved to disk within 1 second after every valid command execution, with no manual saving required.
6. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most tasks faster using commands than using the mouse.
7. The software should be delivered as a single JAR file no larger than 100MB that does not require an installer.
8. The system should provide clear, user-friendly error messages when invalid command formats are entered, instead of terminating or crashing.
9. The system must function fully offline with no internet connection required.
10. Participant names must support Unicode characters to accommodate international names including accented characters (e.g. José, Tomáš), names with apostrophes (e.g. O'Brian), and names with forward slashes (e.g. s/o Kumar).

### Known limitations

- **Phone validation:** Participant phone numbers are restricted to **digits only** (minimum length per `Phone` validation). **Country-code prefixes** (e.g. `+65`), spaces, and punctuation are **rejected**. This behaviour is consistent with the user guide but is **overly strict** for users who naturally type international-format numbers; a future iteration could accept normalised E.164-style input or strip common separators while preserving data integrity.


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
* **Import/Export `FILE_PATH`**: For `import` and `export`, relative paths are resolved from the app's working directory (where the JAR is run), while absolute paths are supported on all mainstream OSes (e.g. `C:/...` or `C:\\...` on Windows, `/...` on macOS/Linux).
--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder.

    1. Double-click the jar file.<br>
       Expected: Shows the GUI with a set of sample events. The window size may not be optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

---

### Adding an event

1. Adding a valid event while in the global event view

    1. Prerequisites: Launch the app. Stay in the global event view (do not enter any event).

    1. Test case: `addevent n/Tech Meetup d/2025-06-15`<br>
       Expected: New event added to the event list. Success message shows the event name, date, and empty location/description.

    1. Test case: `addevent n/Workshop d/2025-07-01 l/NUS Deck desc/Annual hackathon kickoff`<br>
       Expected: Event added with all fields shown in the success message.

    1. Test case: `addevent n/Tech Meetup d/2025-06-15` (duplicate of the first event added above)<br>
       Expected: Error message indicating a duplicate event already exists. No event added.

1. Invalid addevent inputs

    1. Test case: `addevent` (no fields)<br>
       Expected: Error with usage instructions shown.

    1. Test case: `addevent n/ d/2025-06-15` (blank name)<br>
       Expected: Error indicating name constraints.

    1. Test case: `addevent n/Tech Meetup d/not-a-date`<br>
       Expected: Error indicating invalid date format.

1. Adding an event while inside an event participant view

    1. Prerequisites: Enter an event first using `enter event 1`.

    1. Test case: `addevent n/New Event d/2025-08-01`<br>
       Expected: Command fails with a message indicating to leave the current event first.

---

### Editing an event

1. Editing an event while in the global event view

    1. Prerequisites: At least one event in the event list. Stay in the global event view.

    1. Test case: `editevent 1 n/Renamed Event`<br>
       Expected: First event's name is updated. Success message shows the updated event details.

    1. Test case: `editevent 1 l/Marina Bay Sands desc/Updated description`<br>
       Expected: Location and description updated for the first event.

    1. Test case: `editevent 0 n/Invalid`<br>
       Expected: Error message. No event edited.

    1. Other incorrect inputs: `editevent` (no index), `editevent x n/Test` where x is larger than the list size.<br>
       Expected: Error message shown.

---

### Deleting an event

1. Deleting an event while in the global event view

    1. Prerequisites: At least one event in the event list. Stay in the global event view.

    1. Test case: `deleteevent 1`<br>
       Expected: First event deleted. Success message shown.

    1. Test case: `deleteevent 0`<br>
       Expected: Error message. No event deleted.

    1. Other incorrect inputs: `deleteevent`, `deleteevent x` where x is larger than the list size.<br>
       Expected: Error message shown.

1. Deleting an event while inside an event participant view

    1. Prerequisites: Enter an event using `enter event 1`.

    1. Test case: `deleteevent 1`<br>
       Expected: Command fails with a message indicating to leave the current event first.

---

### Entering and leaving an event

1. Entering an event

    1. Prerequisites: At least one event in the event list. Stay in the global event view.

    1. Test case: `enter event 1`<br>
       Expected: App switches to event participant view for the first event. Participant list for that event is shown.

    1. Test case: `enter event 0`<br>
       Expected: Error message. App remains in global event view.

    1. Test case: `enter event x` where x is larger than the event list size.<br>
       Expected: Error message. App remains in global event view.

1. Leaving an event

    1. Prerequisites: Must be inside an event participant view.

    1. Test case: `leave event`<br>
       Expected: App returns to the global event view. Event list is shown.

    1. Test case: `leave event` while already in global event view<br>
       Expected: Error message indicating not currently inside an event.

---

### Adding a participant

1. Adding a valid participant while inside an event

    1. Prerequisites: Enter an event using `enter event 1`.

    1. Test case: `add n/John Doe p/98765432 e/johnd@example.com a/311 Clementi Ave`<br>
       Expected: Participant added. Success message shows all participant fields.

    1. Test case: `add n/Mary Jane p/91234567 e/mary@example.com a/Blk 30 Geylang St r/yes t/python team/Alpha g/maryjane`<br>
       Expected: Participant added with all optional fields. RSVP shown as `yes`, tag as `python`, team as `Alpha`.

    1. Test case: Adding a participant with the same name and same phone number as an existing participant.<br>
       Expected: Error message indicating a duplicate participant already exists.

    1. Test case: Adding a participant with the same name and same email as an existing participant, but a different phone number.<br>
       Expected: Error message indicating a duplicate participant already exists.

    1. Test case: Adding a participant with the same name as an existing participant but different phone AND different email.<br>
       Expected: Participant added successfully (same name but genuinely different contact details allowed).

1. Name field constraints

    1. Test case: `add n/José Martín p/91234567 e/jose@example.com a/123 Street` (Unicode name)<br>
       Expected: Participant added successfully.

    1. Test case: `add n/O'Brian p/91234567 e/obrian@example.com a/123 Street` (apostrophe in name)<br>
       Expected: Participant added successfully.

    1. Test case: `add n/s/o Kumar p/91234567 e/sku@example.com a/123 Street` (slash in name)<br>
       Expected: Participant added successfully.

    1. Test case: `add n/ p/91234567 e/test@example.com a/123 Street` (blank name)<br>
       Expected: Error message with name constraints.

1. Adding outside event participant view

    1. Prerequisites: Stay in the global event view.

    1. Test case: `add n/John Doe p/98765432 e/johnd@example.com a/311 Clementi Ave`<br>
       Expected: Command fails with a message indicating to enter an event first.

---

### Editing a participant

1. Editing a participant while inside an event

    1. Prerequisites: Enter an event with at least one participant.

    1. Test case: `edit 1 p/91112222`<br>
       Expected: First participant's phone updated. Success message shows updated participant details.

    1. Test case: `edit 1 n/New Name e/new@example.com`<br>
       Expected: Name and email updated for the first participant.

    1. Test case: `edit 1 t/` (clear all tags)<br>
       Expected: All tags removed from the first participant.

    1. Test case: `edit 1 team/` (clear team)<br>
       Expected: Team field cleared for the first participant.

    1. Test case: `edit 1` (no fields specified)<br>
       Expected: Error message indicating at least one field must be provided.

    1. Test case: `edit 0 n/Test`<br>
       Expected: Error message. No participant edited.

1. Editing to create a duplicate

    1. Prerequisites: At least two participants in the event.

    1. Test case: Edit participant 2 to have the same name and phone number as participant 1.<br>
       Expected: Error message indicating duplicate participant. No changes applied.

---

### Deleting a participant

1. Deleting a participant while all participants are being shown

    1. Prerequisites: Enter an event so the participant list is shown. Multiple participants in the current event list.

    1. Test case: `delete 1`<br>
       Expected: The first participant is deleted from the list. Details of the deleted participant are shown in the status message.

    1. Test case: `delete 0`<br>
       Expected: No participant is deleted. Error details shown in the status message.

    1. Other incorrect delete commands to try: `delete`, `delete x` (where x is larger than the list size).<br>
       Expected: Error message shown.

---

### Assigning a team

1. Assigning a team while inside an event

    1. Prerequisites: Enter an event with at least one participant.

    1. Test case: `assign 1 team/Alpha`<br>
       Expected: First participant assigned to team `Alpha`. Success message shown.

    1. Test case: `assign 1 team/` (clear team)<br>
       Expected: Team cleared for the first participant.

    1. Test case: `assign 0 team/Alpha`<br>
       Expected: Error message. No assignment made.

    1. Test case: `assign 1 team/ThisTeamNameIsTooLong` (team name over 15 characters)<br>
       Expected: Error message indicating team name constraints.

---

### Checking in a participant

1. Checking in while inside an event

    1. Prerequisites: Enter an event with at least one participant.

    1. Test case: `checkin 1`<br>
       Expected: First participant marked as checked in. Success message shown. Check-in status updated in the participant card.

    1. Test case: `checkin 0`<br>
       Expected: Error message. No check-in performed.

---

### Viewing a participant

1. Viewing participant details while inside an event

    1. Prerequisites: Enter an event with at least one participant.

    1. Test case: `view 1`<br>
       Expected: Full details of the first participant displayed in the detail panel, including all fields (name, phone, email, address, GitHub, team, RSVP, tags, check-in status).

    1. Test case: `view 0`<br>
       Expected: Error message. No participant details shown.

    1. Test case: `view x` where x is larger than the list size.<br>
       Expected: Error message shown.

1. Viewing after filtering

    1. Prerequisites: Apply a filter first (e.g. `filter r/yes`), then run `view 1`.

    1. Test case: `view 1`<br>
       Expected: Details of the first participant in the *filtered* list are shown (not the first participant of the full list).

---

### Searching

1. Searching in the global event view

    1. Prerequisites: Stay in the global event view with at least a few events.

    1. Test case: `search tech` (assuming an event with "tech" in the name exists)<br>
       Expected: Event list filtered to show only events whose name matches "tech". Status message shows count.

    1. Test case: `search` (no keywords)<br>
       Expected: Error message with usage instructions shown.

1. Searching inside an event participant view

    1. Prerequisites: Enter an event with multiple participants.

    1. Test case: `search John`<br>
       Expected: Participant list filtered to show only participants matching "John". Status message shows count.

    1. Test case: `search john doe` (multiple keywords)<br>
       Expected: Participants matching either "john" or "doe" are shown.

    1. Test case: `list` after a search.<br>
       Expected: Full list restored.

---

### Filtering participants

1. Filtering while inside an event

    1. Prerequisites: Enter an event with participants that have a mix of RSVP values, tags, team assignments, and check-in states.

    1. Test case: `filter r/yes`<br>
       Expected: Only participants with RSVP `yes` shown. Status message shows count.

    1. Test case: `filter r/no`<br>
       Expected: Only participants with RSVP `no` shown.

    1. Test case: `filter r/pending`<br>
       Expected: Only participants with RSVP `pending` shown.

    1. Test case: `filter t/python` (assuming a participant with tag `python` exists)<br>
       Expected: Only participants with that tag shown.

    1. Test case: `filter team/Alpha` (assuming a participant assigned to `Alpha` exists)<br>
       Expected: Only participants on team `Alpha` shown.

    1. Test case: `filter checkin/yes`<br>
       Expected: Only checked-in participants shown.

    1. Test case: `filter checkin/no`<br>
       Expected: Only participants not checked in shown.

    1. Test case: `list` after a filter.<br>
       Expected: Full participant list restored.

1. Invalid filter inputs

    1. Test case: `filter` (no arguments)<br>
       Expected: Error message with usage instructions.

    1. Test case: `filter r/yes t/python` (two criteria)<br>
       Expected: Error message. Only one filter criterion allowed per command.

    1. Test case: `filter r/invalid`<br>
       Expected: Error message indicating invalid RSVP value.

1. Filtering outside event participant view

    1. Prerequisites: Stay in global event view.

    1. Test case: `filter r/yes`<br>
       Expected: Command fails with a message indicating to enter an event first.

---

### Viewing statistics

1. Viewing statistics while inside an event

    1. Prerequisites: Enter an event with participants having varied RSVP, check-in, and team data.

    1. Test case: `statistics`<br>
       Expected: Statistics panel displayed showing RSVP breakdown, check-in counts, and team distribution.

    1. Test case: `statistics abc` (extra arguments)<br>
       Expected: Error message. Statistics not shown.

1. Viewing statistics on an empty participant list

    1. Prerequisites: Enter an event with no participants (or `clear` all participants first).

    1. Test case: `statistics`<br>
       Expected: Statistics panel shown with zero counts across all categories.

1. Viewing statistics outside event participant view

    1. Prerequisites: Stay in global event view.

    1. Test case: `statistics`<br>
       Expected: Command fails with a message indicating to enter an event first.

---

### Clearing all participants

1. Clearing while inside an event

    1. Prerequisites: Enter an event with at least one participant.

    1. Test case: `clear`<br>
       Expected: All participants removed from the current event. Success message shown.

    1. Test case: `clear abc` (extra arguments)<br>
       Expected: Error message. No participants cleared.

1. Clearing outside event participant view

    1. Prerequisites: Stay in global event view.

    1. Test case: `clear`<br>
       Expected: Command fails with a message indicating to enter an event first.

---

### Importing participants from CSV

1. Importing a valid CSV file

    1. Prerequisites: Enter an event. Prepare a CSV file at a known path (e.g. `data/participants.csv`) with valid participant data.

    1. Test case: `import data/participants.csv`<br>
       Expected: Participants from the CSV added to the current event. Success message shows number of participants imported.

    1. Test case: `import list`<br>
       Expected: A list of previously imported files shown (or an empty list if none).

1. Importing an invalid or missing file

    1. Test case: `import data/nonexistent.csv`<br>
       Expected: Error message indicating file not found.

    1. Test case: `import` (no path)<br>
       Expected: Error message with usage instructions.

1. Importing outside event participant view

    1. Prerequisites: Stay in global event view.

    1. Test case: `import data/participants.csv`<br>
       Expected: Command fails with a message indicating to enter an event first.

---

### Exporting participants to CSV

1. Exporting while inside an event

    1. Prerequisites: Enter an event with at least one participant.

    1. Test case: `export data/exports/output.csv`<br>
       Expected: CSV file created at the specified path. Success message shown. Open the file to verify participant data is correct.

    1. Test case: `export` (no path — uses default path if supported)<br>
       Expected: File exported to default location. Success message shown.

1. Exporting outside event participant view

    1. Prerequisites: Stay in global event view.

    1. Test case: `export data/exports/output.csv`<br>
       Expected: Command fails with a message indicating to enter an event first.

---

### Single-word utility commands

1. List command

    1. Test case: `list` in global event view.<br>
       Expected: Full event list shown.

    1. Test case: `list` inside an event participant view.<br>
       Expected: Full participant list for the current event shown.

    1. Test case: `list abc` (extra arguments)<br>
       Expected: Error message. List not executed.

1. Help command

    1. Test case: `help`<br>
       Expected: Help window opened showing a link to the user guide.

    1. Test case: `help abc` (extra arguments)<br>
       Expected: Error message. Help window not opened.

1. Switch theme command

    1. Test case: `switchtheme dark`<br>
       Expected: App theme switches to dark mode.

    1. Test case: `switchtheme light`<br>
       Expected: App theme switches to light mode.

    1. Test case: `switchtheme invalid`<br>
       Expected: Error message indicating valid values are `dark` or `light`.

---

### Saving data

1. Automatic saving after commands

    1. Prerequisites: Add a new event or participant.

    1. Close the app and re-launch it.<br>
       Expected: The newly added event or participant is still present. All data persisted automatically.

1. Dealing with a missing data file

    1. Navigate to the `data/` folder and delete `eventbook.json`.

    1. Re-launch the app.<br>
       Expected: App launches with the default sample data restored.

1. Dealing with a corrupted data file

    1. Open `data/eventbook.json` in a text editor and introduce invalid JSON (e.g. delete a closing brace).

    1. Re-launch the app.<br>
       Expected: App launches with an empty event list and a warning that the data file was corrupted.

1. Dealing with a corrupted addressbook file

    1. Enter an event and note which folder the participant data is stored in. Open the corresponding `addressbook.json` and corrupt it similarly.

    1. Re-launch the app and enter the same event.<br>
       Expected: Participant list for that event is empty; other events' data unaffected.
