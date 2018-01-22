import static org.junit.Assert.*;
import org.junit.Test;

import MakeSchedule.Scheduler;

public class schedulerTest {
	Scheduler sc = new Scheduler();

	@Test
	public void testLogic1(){
		String testInput = 
				"Math:(Monday 19:00-20:00)(Monday 19:00-20:00) "
				+ "\n" 
				+"Literature:(Monday 22:00-23:00)";
		String testResult = 
				"~"
				+"\n"
				+"Math:(Monday 19:00 - 20:00)"
				+"\n"
				+"Literature:(Monday 22:00 - 23:00)"
				+"\n"
				+"~"
				+"\n"
				+"Math:(Monday 19:00 - 20:00)"
				+"\n"
				+"Literature:(Monday 22:00 - 23:00)"
				+"\n";
		String result = sc.doWork(testInput);
		assertEquals(result, testResult);
	}
	
	@Test
	public void testLogic2(){
		String testInput = 
				"Math:()(Monday 19:00-20:00) "
				+ "\n" 
				+"Literature:(Monday 22:00-23:00)";
		String testResult = 
				"~"
				+"\n"
				+"Math:(Monday 19:00 - 20:00)"
				+"\n"
				+"Literature:(Monday 22:00 - 23:00)"
				+"\n"
				;
		String result = sc.doWork(testInput);
		assertEquals(result, testResult);
	}
	
		@Test
	public void testLogic3(){
		String testInput = 
				"Math:()( ) "
				+ "\n" 
				+"Literature:(Monday 22:00-23:00)"
				+ "\n" 
				+"Literature:(Monday 22:00-23:00)"
				+ "\n" 
				+"Literature:(Monday 22:00-23:00)"
				+ "\n" 
				+"Literature:(Monday 22:00-23:00)"
				+ "\n" 
				+"Literature:(Monday 22:00-23:00)"
				;
		String testResult = 
				"No schedule for these courses can be constructed. Delete or change a course"
				;
		String result = sc.doWork(testInput);
		assertEquals(result, testResult);
	}
		
		@Test
		public void testLogic4(){
			String testInput = 
					"Math:(Monday 19:00-20:00)(Monday 19:00-20:00) "+ "\n" 
							+" ";
			String testResult = "~" + "\n" 
					+ "Math:(Monday 19:00 - 20:00)"+ "\n"
					+ "~" + "\n"
					+ "Math:(Monday 19:00 - 20:00)"+ "\n"
					;
			String result = sc.doWork(testInput);
			assertEquals(result, testResult);
		}
	
	@Test
	public void testStructure1(){
		String testInput = 
				""
				;
		String testResult = 
				"No schedule for these courses can be constructed. Delete or change a course"
				;
		String result = sc.doWork(testInput);
		assertEquals(result, testResult);
	}
	
	@Test
	public void testStructure2(){
		String testInput = 
				"Monday"
				;
		String testResult = 
				"No schedule for these courses can be constructed. Delete or change a course"
				;
		String result = sc.doWork(testInput);
		assertEquals(result, testResult);
	}
	
	@Test
	public void testStructure3(){
		String testInput = 
				"Tuesday:"
				;
		String testResult = 
				"No schedule for these courses can be constructed. Delete or change a course"
				;
		String result = sc.doWork(testInput);
		assertEquals(result, testResult);
	}
	
	@Test
	public void testStructure4(){
		String testInput = 
				"Tuesday:nowaystuff"
				;
		String testResult = 
				"No schedule for these courses can be constructed. Delete or change a course"
				;
		String result = sc.doWork(testInput);
		assertEquals(result, testResult);
	}
	

	@Test
	public void testStructure5(){
		String testInput = 
				"Tuesday:nowaystuff()"
				;
		String testResult = 
				"No schedule for these courses can be constructed. Delete or change a course"
				;
		String result = sc.doWork(testInput);
		assertEquals(result, testResult);
	}
	
	@Test
	public void testStructure6(){
		String testInput = 
				"Tuesday:(nowaystuff)"
				;
		String testResult = 
				"No schedule for these courses can be constructed. Delete or change a course"
				;
		String result = sc.doWork(testInput);
		assertEquals(result, testResult);
	}
	
	@Test
	public void testStructure7(){
		String testInput = 
				"Tuesday:(nowaystuff1:0-15:40)"
				;
		String testResult = 
				"No schedule for these courses can be constructed. Delete or change a course"
				;
		String result = sc.doWork(testInput);
		assertEquals(result, testResult);
	}
	
	
	
	@Test
	public void testStructure8(){
		String testInput = 
				"Math:(Monday 19:00-20:00)(Monday 19:00-20:00) "+ "\n" 
						+"Literature:(Monday 1i:00-23:00)";
		String testResult = 
				"No schedule for these courses can be constructed. Delete or change a course"
				;
		String result = sc.doWork(testInput);
		assertEquals(result, testResult);
	}
	
	
}
