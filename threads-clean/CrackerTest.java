import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CrackerTest {
    @Test
    public void testConverts(){
        String expectedHash = "66b27417d37e024c46526c2f6d358a754fc552f3";
        String result = Cracker.hexToString(Cracker.hexToArray(expectedHash));
        assertEquals(expectedHash, result);
    }
    @Test
    public void testToHash(){
        String input = "xyz";
        String expectedHash = "66b27417d37e024c46526c2f6d358a754fc552f3";
        assertEquals(expectedHash, Cracker.hexToString(Cracker.toHash(input)));
    }

    @Test
    public void testFindResult1() throws InterruptedException {
        String expected = "xyz";
        Cracker cracker = new Cracker();
        String result = cracker.findResult("66b27417d37e024c46526c2f6d358a754fc552f3", 3, 4);
        assertEquals(expected, result);
    }

    @Test
    public void testFindResult2() throws InterruptedException {
        String expected = "a!";
        Cracker cracker = new Cracker();
        String result = cracker.findResult("34800e15707fae815d7c90d49de44aca97e2d759", 3, 4);
        assertEquals(expected, result);
    }

    @Test
    public void testFindResult3() throws InterruptedException {
        String expected = "fm";
        Cracker cracker = new Cracker();
        String result = cracker.findResult("adeb6f2a18fe33af368d91b09587b68e3abcb9a7", 3, 4);
        assertEquals(expected, result);
    }
}
