package seedu.address.model.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.EventBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.GitHub;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RsvpStatus;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    /**
     * Returns an array of sample {@code Event} objects with participants pre-loaded.
     */
    public static Event[] getSampleEvents() {
        Person[] meetupParticipants = new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                Optional.empty(), getTagSet("frontend"), new GitHub("alexyeoh"), new RsvpStatus("yes")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                Optional.empty(), getTagSet("backend"), new GitHub("berniceyu"), new RsvpStatus("yes")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                Optional.empty(), getTagSet("design"), null, new RsvpStatus("pending")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                Optional.empty(), getTagSet("backend"), new GitHub("lidavid"), new RsvpStatus("no"))
        };

        Person[] hackathonParticipants = new Person[] {
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                Optional.empty(), getTagSet("fullstack"), null, new RsvpStatus("yes")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                Optional.empty(), getTagSet("devops"), new GitHub("royb"), new RsvpStatus("yes")),
            new Person(new Name("Priya Sharma"), new Phone("91234567"), new Email("priya@example.com"),
                new Address("Blk 12 Bishan Street 12, #05-10"),
                Optional.empty(), getTagSet("ai"), new GitHub("priyasharma"), new RsvpStatus("pending"))
        };

        Person[] workshopParticipants = new Person[] {
            new Person(new Name("Wei Ming"), new Phone("98765432"), new Email("weiming@example.com"),
                new Address("Blk 22 Clementi Ave 4, #03-15"),
                Optional.empty(), getTagSet("student"), new GitHub("weiming"), new RsvpStatus("yes")),
            new Person(new Name("Siti Rahimah"), new Phone("87654321"), new Email("siti@example.com"),
                new Address("Blk 5 Woodlands Drive 14, #08-22"),
                Optional.empty(), getTagSet("student"), null, new RsvpStatus("yes")),
            new Person(new Name("Jason Tan"), new Phone("91122334"), new Email("jasontan@example.com"),
                new Address("Blk 88 Queenstown Road, #12-01"),
                Optional.empty(), getTagSet("professional"), new GitHub("jasontan"), new RsvpStatus("pending"))
        };

        Event meetup = new Event(new EventName("Singapore Tech Meetup"),
                new EventDate("2026-06-15"),
                Optional.of("Marina Bay Sands Expo Hall 4"),
                Optional.of("Monthly networking meetup for Singapore tech community"));
        for (Person p : meetupParticipants) {
            meetup.addParticipant(p);
        }

        Event hackathon = new Event(new EventName("NUS Hackathon 2026"),
                new EventDate("2026-07-20"),
                Optional.of("NUS School of Computing, COM1"),
                Optional.of("24-hour hackathon open to all NUS students and alumni"));
        for (Person p : hackathonParticipants) {
            hackathon.addParticipant(p);
        }

        Event workshop = new Event(new EventName("AI Workshop Series"),
                new EventDate("2026-08-10"),
                Optional.of("Singapore Management University, SMU"),
                Optional.of("Introductory workshop on machine learning and AI applications"));
        for (Person p : workshopParticipants) {
            workshop.addParticipant(p);
        }

        return new Event[] { meetup, hackathon, workshop };
    }

    /**
     * Returns an a sample of {@code ReadOnlyEventBook} with sample events and participants.
     */
    public static ReadOnlyEventBook getSampleEventBook() {
        EventBook sampleEb = new EventBook();
        for (Event sampleEvent : getSampleEvents()) {
            sampleEb.addEvent(sampleEvent);
        }
        return sampleEb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
