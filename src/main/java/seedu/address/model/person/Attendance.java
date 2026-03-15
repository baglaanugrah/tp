package seedu.address.model.person;

/**
 * Represents the attendance status of a person.
 * The status indicates whether the person has checked in or not.
 */
public class Attendance {

    /** Indicates whether the person has checked in. */
    private boolean status;

    /**
     * Constructs an {@code Attendance} object with default status
     * set to {@code false} (Not Checked-In).
     */
    public Attendance() {
        this.status = false;
    }
    /**
     * Constructs an {@code Attendance} object with the specified status.
     *
     * @param status The attendance status. {@code true} if checked in,
     *               {@code false} otherwise.
     */

    public Attendance(boolean status) {
        this.status = status;
    }

    /**
     * Returns the current attendance status.
     *
     * @return {@code true} if the person has checked in,
     *         {@code false} otherwise.
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Updates the attendance status.
     *
     * @param status The new attendance status.
     *               {@code true} if checked in, {@code false} otherwise.
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status ? "Checked-In" : "Not Checked-In";
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Attendance
                && status == ((Attendance) other).status);
    }
}
