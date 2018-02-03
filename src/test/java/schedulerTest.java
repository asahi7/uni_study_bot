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
				"~ Combination 1 ~"
				+"\n"
				+"Math: (Monday 19:00 - 20:00)"
				+"\n"
				+"Literature: (Monday 22:00 - 23:00)"
				+"\n"+"\n"
				+"~ Combination 2 ~"
				+"\n"
				+"Math: (Monday 19:00 - 20:00)"
				+"\n"
				+"Literature: (Monday 22:00 - 23:00)"
				+"\n"+"\n";
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
				"~ Combination 1 ~"
				+"\n"
				+"Math: (Monday 19:00 - 20:00)"
				+"\n"
				+"Literature: (Monday 22:00 - 23:00)"
				+"\n" +"\n"
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
		String testResult = "~ Combination 1 ~" + "\n"
				+ "Math: (Monday 19:00 - 20:00)"+ "\n"+"\n"
				+ "~ Combination 2 ~" + "\n"
				+ "Math: (Monday 19:00 - 20:00)"+ "\n" +"\n"
				;
		String result = sc.doWork(testInput);
		assertEquals(result, testResult);
	}


	@Test
	public void testLogic5(){
		String testInput =
				"Math:(Monday 9:00-10:00) "+ "\n"
						+"Literature :(Monday 9:00-10:00) ";
		String testResult = "No schedule for these courses can be constructed. Delete or change a course"
				;
		String result = sc.doWork(testInput);
		assertEquals(result, testResult);
	}


	@Test
	public void testLogic6(){
		String testInput =
				"Math:(Monday 9:00-11:00) "+ "\n"
						+"Literature :(Monday 9:00-10:00) ";
		String testResult = "No schedule for these courses can be constructed. Delete or change a course"
				;
		String result = sc.doWork(testInput);
		assertEquals(result, testResult);
	}

	@Test
	public void testLogic7(){
		String testInput =
				"Math:(Monday 19:00-20:00)(Monday 19:00-20:00)  "+ "\n"
						+"Literature :(Monday 19:00-20:00) ";
		String testResult = "No schedule for these courses can be constructed. Delete or change a course"
				;
		String result = sc.doWork(testInput);
		assertEquals(result, testResult);
	}


	@Test
	public void testLogic8(){
		String testInput =
				"Math: (Monday 10:30-12:30, Tuesday 10:30 -12:30) (Monday 12:30-14:40)"+ "\n"
						+"Literature: (Monday  12:30-14:40)";
		String testResult = "~ Combination 1 ~" + "\n"+
				"Math: (Monday 10:30 - 12:30,Tuesday 10:30 - 12:30)"+ "\n"+
				"Literature: (Monday 12:30 - 14:40)"+ "\n"+ "\n"
				;
		String result = sc.doWork(testInput);
		assertEquals(result, testResult);
	}


	@Test
	public void testLogic9(){
		String testInput =
				"Math:(Monday 9:00-12:00,Wednesday 9:00-12:00)(Tuesday 9:00-12:00)" + "\n"+
		                "English:(Tuesday 9:00-12:00,Thursday 9:00-12:00)" + "\n"+
		                "Literature:(Monday 12:00-15:00,Wednesday 12:00  -  15:00)(Tuesday 12:00-15:00)" + "\n"+
		                "History:(Tuesday 12:00-15:00,Thursday 9:00-15:00)(Wednesday 12:00-15:00)";
		String testResult =  "~ Combination 1 ~" + "\n"+
				"Math: (Monday 9:00 - 12:00,Wednesday 9:00 - 12:00)"+ "\n"+
				"English: (Thursday 9:00 - 12:00,Tuesday 9:00 - 12:00)"+ "\n"+
				"Literature: (Tuesday 12:00 - 15:00)"+ "\n"+
				"History: (Wednesday 12:00 - 15:00)"+ "\n"+"\n"
				;
		String result = sc.doWork(testInput);
		assertEquals(result, testResult);
	}


	@Test
	public void testLogic10(){
		String testInput =
				"General Biology:(Monday 10:30-11:45, Wednesday 10:30-11:45)(Monday 13:00-14:15, Wednesday 13:00-14:15)" + "\n"+
		                "GeneralChemistry:(Tuesday 9:00-11:30)(Tuesday 13:00-15:30)(Wednesday 9:00-11:30)";
		String testResult =
				"~ Combination 1 ~" + "\n"+
				"GeneralBiology: (Monday 10:30 - 11:45,Wednesday 10:30 - 11:45)"+ "\n"+
				"GeneralChemistry: (Tuesday 9:00 - 11:30)"+ "\n"+"\n"+
				"~ Combination 2 ~" + "\n"+
				"GeneralBiology: (Monday 10:30 - 11:45,Wednesday 10:30 - 11:45)"+ "\n"+
				"GeneralChemistry: (Tuesday 13:00 - 15:30)"+ "\n"+"\n"+
				"~ Combination 3 ~" + "\n"+
				"GeneralBiology: (Monday 13:00 - 14:15,Wednesday 13:00 - 14:15)"+ "\n"+
				"GeneralChemistry: (Tuesday 9:00 - 11:30)"+ "\n"+"\n"+
				"~ Combination 4 ~" + "\n"+
				"GeneralBiology: (Monday 13:00 - 14:15,Wednesday 13:00 - 14:15)"+ "\n"+
				"GeneralChemistry: (Tuesday 13:00 - 15:30)"+ "\n"+"\n"+
				"~ Combination 5 ~" + "\n"+
				"GeneralBiology: (Monday 13:00 - 14:15,Wednesday 13:00 - 14:15)"+ "\n"+
				"GeneralChemistry: (Wednesday 9:00 - 11:30)"+ "\n"+"\n"
				;
		String result = sc.doWork(testInput);
		assertEquals(result, testResult);
	}

	@Test
	public void testLogic11(){
		String testInput =
				"Math:(Monday 00:00-01:00)(Monday 01:00-02:00)"
				+ "(Monday 02:00-03:00)"
				+ "(Monday 04:00-05:00)";
		String testResult =
				"~ Combination 1 ~\n"+
				"Math: (Monday 00:00 - 1:00)\n\n"+
				"~ Combination 2 ~\n"+
				"Math: (Monday 1:00 - 2:00)\n\n"+
				"~ Combination 3 ~\n"+
				"Math: (Monday 2:00 - 3:00)\n\n"+
				"~ Combination 4 ~\n"+
				"Math: (Monday 4:00 - 5:00)\n\n";
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


	@Test
	public void testStructure9(){
		String testInput =
				"Math:(Monday 00:00-01:00)(Monday 01:00-02:00)"
				+ "(Monday 02:00-03:00)(Monday 04:00-05:00)"
				+ "(Monday 05:00-06:00)"+ "(Monday 06:00-07:00)";
		String testResult =
				"No schedule for these courses can be constructed. Delete or change a course"
				;
		String result = sc.doWork(testInput);
		assertEquals(result, testResult);
	}

}
