import java.util.LinkedList;
import java.util.HashMap;

	public class Unit{
		private class tmp_unit_exp{
			String uni;
			int deg;
			private tmp_unit_exp(String u,int e) {uni=u;deg=e;}
		}
		
		private static java.util.HashMap<String,String> UnitList = new HashMap<String,String>(); 
		private String name;
		private String formula;
		public Unit(String name, String formula) {this.name=name;this.formula=formula;UnitList.put(name, formula);}
		public String getName() {return name;}
		public String getFormula() {return formula;}
		public Unit Unit_Mult(Unit n) {
			LinkedList<tmp_unit_exp> tmp = new LinkedList<tmp_unit_exp>();
			String s1 = n.getFormula();
			String s2 = this.formula;
			//Code to parse the units and degree to the linked list
			return new Unit("","");
		}
		
		
		public static boolean CheckList_name(String name) {
			if (UnitList.containsKey(name)){return true;}
			return false;
			}
	}
