import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import java.util.LinkedList;

import static org.junit.Assert.*;


public class CourseTest {

    private static Course testCourse;
    private static Student steven;
    private static Student josh;
    private static Student amanda;

    @Before
    public void setUp() {
        LinkedList<Course> stevenList = new LinkedList<>();
        steven = new Student("Steven", stevenList);
        LinkedList<Course> joshList = new LinkedList<>();
        josh = new Student("Josh", joshList);
        LinkedList<Course> amandaList = new LinkedList<>();
        amanda = new Student("Amanda", joshList);


        LinkedList<Student> coursePref = new LinkedList<>();
        coursePref.add(steven);
        coursePref.add(josh);
        coursePref.add(amanda);
        testCourse = new Course("160", 1, coursePref);
    }

    @Test(timeout = 1000)
    public void testConstructor() {
        testCourse = new Course("120", 0);
        assertEquals("120", testCourse.toString());
        assertFalse(testCourse.willAcceptStudent(steven));
        assertEquals(testCourse.toString(), testCourse.getCourseCode());
        LinkedList<Student> coursePref = new LinkedList<>();
        coursePref.add(steven);
        testCourse.setPreferences(coursePref);
        assertEquals(testCourse.getPreferences().size(), 1);
        testCourse.setCapacity(1);
        assertTrue(testCourse.willAcceptStudent(steven));
    }

    @Test(timeout = 1000)
    public void testWillAcceptNotCapacity() {
        assertTrue(testCourse.willAcceptStudent(steven));
    }

    @Test(timeout = 1000)
    public void testWillAcceptAtCapacityNotPreferred() {
        assertTrue(testCourse.willAcceptStudent(josh));
        testCourse.acceptStudent(steven);
        assertFalse(testCourse.willAcceptStudent(josh));
    }

    @Test(timeout = 1000)
    public void testWillAcceptAtCapacityPreferred() {
        assertTrue(testCourse.willAcceptStudent(amanda));
        testCourse.acceptStudent(amanda);
        assertTrue(testCourse.willAcceptStudent(josh));
        testCourse.acceptStudent(josh);
        Iterator iter = testCourse.getAccepted().iterator();
        assertTrue(iter.hasNext());
        assertEquals(josh, iter.next());
        assertFalse(iter.hasNext());
    }

    @Test(timeout = 1000)
    public void testPreferencesInOrder() {
        testCourse.setCapacity(2);
        testCourse.acceptStudent(josh);
        testCourse.acceptStudent(steven);
        assertFalse(testCourse.willAcceptStudent(amanda));
        Iterator iter = testCourse.getAccepted().iterator();
        assertEquals(iter.next(), steven);
        assertEquals(iter.next(), josh);
        assertFalse(iter.hasNext());
    }

    @Test(timeout = 1000)
    public void testStudentNotInPreferences() {
        testCourse.getPreferences().remove(amanda);
        assertFalse(testCourse.willAcceptStudent(amanda));
        assertNull(testCourse.acceptStudent(amanda));
    }

    @Test(timeout = 1000)
    public void testStudentAlreadyAccepted() {
        testCourse.acceptStudent(josh);
        assertFalse(testCourse.willAcceptStudent(josh));
    }

    @Test(timeout = 1000)
    public void testAcceptNotCapacity() {
        testCourse.acceptStudent(steven);
        Iterator iter = testCourse.getAccepted().iterator();
        assertTrue(iter.hasNext());
        assertEquals(steven, iter.next());
        assertFalse(iter.hasNext());
    }

    @Test(timeout = 1000)
    public void testInOrder() {
        testCourse.setCapacity(2);
        testCourse.acceptStudent(steven);
        testCourse.acceptStudent(amanda);
        Iterator iter = testCourse.getAccepted().iterator();
        assertEquals(iter.next(), steven);
        assertEquals(iter.next(), amanda);
        assertFalse(iter.hasNext());
    }

    @Test(timeout = 1000)
    public void testHashCode() {
        assertEquals(3, testCourse.hashCode());
    }
}
