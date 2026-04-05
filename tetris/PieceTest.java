

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece square;
	private Piece s1, s1rotated,s2,s2rotated;
	private Piece l1,l1rotated,l2,l2rotated;
	private Piece st, strotated;
	@BeforeEach
	protected void setUp() throws Exception {


		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();

		square = new Piece(Piece.SQUARE_STR);

		st = new Piece(Piece.STICK_STR);
		strotated = st.computeNextRotation();

		s1 = new Piece(Piece.S1_STR);
		s1rotated = s1.computeNextRotation();

		s2 = new Piece(Piece.S2_STR);
		s2rotated = s2.computeNextRotation();

		l1 = new Piece(Piece.L1_STR);
		l1rotated = l1.computeNextRotation();

		l2 = new Piece(Piece.L2_STR);
		l2rotated = l2.computeNextRotation();
	}

	// Here are some sample tests to get you started
	@Test
	public void testSampleSize() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());

		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());

		assertEquals(1, st.getWidth());
		assertEquals(4, st.getHeight());

		assertEquals(2, square.getWidth());
		assertEquals(2, square.getHeight());

		assertEquals(3, s1.getWidth());
		assertEquals(2, s1.getHeight());

		assertEquals(2, s1rotated.getWidth());
		assertEquals(3, s1rotated.getHeight());

		assertEquals(2, l1.getWidth());
		assertEquals(3, l1.getHeight());

		assertEquals(3, l1rotated.getWidth());
		assertEquals(2, l1rotated.getHeight());

	}


	// Test the skirt returned by a few pieces
	@Test
	public void testSampleSkirt() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));

		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, s1rotated.getSkirt()));

		assertTrue(Arrays.equals(new int[] {0, 0}, l1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, l1rotated.getSkirt()));

		assertTrue(Arrays.equals(new int[] {0, 0}, square.getSkirt()));

		assertTrue(Arrays.equals(new int[] {0}, st.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0, 0}, strotated.getSkirt()));


	}
	@Test
	public void testrotation(){
		assertTrue(square.equals(square.computeNextRotation()));
		assertTrue(pyr1.equals(pyr4.computeNextRotation()));
		assertTrue(s1.equals(s1rotated.computeNextRotation()));
		assertFalse(st.equals(strotated));
	}
	@Test
	public void testfastrotation(){
		Piece[] pieces = Piece.getPieces();

		assertTrue(pieces[Piece.PYRAMID].fastRotation().equals(pyr2));
		assertTrue(pieces[Piece.S1].fastRotation().equals(s1rotated));
		assertTrue(pieces[Piece.L1].fastRotation().equals(l1rotated));
		assertTrue(pieces[Piece.S2].fastRotation().equals(s2rotated));
		assertTrue(pieces[Piece.L2].fastRotation().equals(l2rotated));
		assertTrue(pieces[Piece.S1].fastRotation().equals(s1rotated));
		assertTrue(pieces[Piece.STICK].fastRotation().equals(strotated));
		assertTrue(pieces[Piece.SQUARE].fastRotation().equals(square));
	}

}