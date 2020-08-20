package org.middlepath.mcapi;

import org.junit.Test;
import org.middlepath.mcapi.generic.Coordinate;
import static org.junit.Assert.*;

import java.util.Arrays;

public class CoordinateTest {

	@Test
	public void testEquals() throws Exception {
		Coordinate a = new Coordinate(42, 32, 22);
		Coordinate b = new Coordinate(42, 32, 22);
		Coordinate c = new Coordinate(42, 32, 21);
		Coordinate d = new Coordinate(42, -32, -1);
		Coordinate e = new Coordinate(12, 32, 22);
		
		assertEquals(a, b);
		assertNotEquals(a, c);
		assertNotEquals(a, d);
		assertEquals(d, d);
		assertNotEquals(a, e);
	}
	
	@Test
	public void testComparable() throws Exception {
		Coordinate[] coords = new Coordinate[] {
			new Coordinate(2,2,2), new Coordinate(3,2,3), new Coordinate(3,2,3),
			new Coordinate(16,16,17), new Coordinate(16,16,30135), new Coordinate(16,17,30138),
			new Coordinate(16,16,30128), new Coordinate(16,16,30127), new Coordinate(16,17,30127)
		};
		
		Coordinate[] sortCoords = new Coordinate[coords.length];
		System.arraycopy(coords, 0, sortCoords, 0, coords.length);
		Arrays.sort(sortCoords);
		
		assertEquals(coords[0], sortCoords[0]);
		assertEquals(coords[1], sortCoords[1]);
		assertEquals(coords[1], sortCoords[2]);
		assertEquals(coords[3], sortCoords[3]);
		assertEquals(coords[7], sortCoords[4]);
		assertEquals(coords[8], sortCoords[5]);
		assertEquals(coords[6], sortCoords[6]);
		assertEquals(coords[4], sortCoords[7]);
		assertEquals(coords[5], sortCoords[8]);
		
	}
}
