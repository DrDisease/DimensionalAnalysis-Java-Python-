import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;

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
		
		public static boolean CheckList_name(String name) {
			if (UnitList.containsKey(name)){return true;}
			return false;
			}
		
		
		
		public void setName(String n) {this.name=n;}
		
		//Formulas must come in Strings (s(^INT)?('*'(^INT)?)*)
		public Unit Unit_Mult(Unit n) {
			LinkedList<tmp_unit_exp> tmp = new LinkedList<tmp_unit_exp>();
			String s1 = n.getFormula();
			String s2 = this.formula;
			//Code to parse the units and degree to the linked list
			
			String[] s1_units = s1.split("[*]");
			String[] s2_units = s2.split("[*]");
			int i ;
			Iterator<tmp_unit_exp> iter = tmp.iterator();
			String tmp_str;
			for(String s : s1_units) {
				if(s.contains("^^")) {
					tmp_str =s.substring(0, s.indexOf("^")) ;
					i = Integer.parseInt(s.substring(s.indexOf("^")+1));
				}
				else {
					tmp_str = s;
					i = 1;
				}
					tmp_unit_exp tmp_var = null;
					boolean control = false;
					while(iter.hasNext()) {
						tmp_var = iter.next();
						if(tmp_var.uni.equals(tmp_str)) {control = true;break;}
					}
					if(control) {tmp_var.deg+=i;}
					else {tmp.add(new tmp_unit_exp("s",i));}
				
			}
			for(String s : s2_units) {
				if(s.contains("^^")) {
					tmp_str =s.substring(0, s.indexOf("^")) ;
					i = Integer.parseInt(s.substring(s.indexOf("^")+1));
				}
				else {
					tmp_str = s;
					i = 1;
				}
					tmp_unit_exp tmp_var = null;
					boolean control = false;
					while(iter.hasNext()) {
						tmp_var = iter.next();
						if(tmp_var.uni.equals(tmp_str)) {control = true;break;}
					}
					if(control) {tmp_var.deg+=i;}
					else {tmp.add(new tmp_unit_exp("s",i));}
			}
			String result_final="";
			for(tmp_unit_exp t : tmp) {
			if(t.deg != 0) {result_final += t.uni+"^"+t.deg+"*";}
			}
			result_final = result_final.substring(0,result_final.length()-1);
			return new Unit("UNDEFINED_VAR","result_final");
		}
		
		public Unit Unit_Div(Unit n) {
			LinkedList<tmp_unit_exp> tmp = new LinkedList<tmp_unit_exp>();
			String s1 = n.getFormula();
			String s2 = this.formula;
			//Code to parse the units and degree to the linked list
			
			String[] s1_units = s1.split("[*]");
			String[] s2_units = s2.split("[*]");
			int i ;
			Iterator<tmp_unit_exp> iter = tmp.iterator();
			String tmp_str;
			for(String s : s1_units) {
				if(s.contains("^^")) {
					tmp_str =s.substring(0, s.indexOf("^")) ;
					i = Integer.parseInt(s.substring(s.indexOf("^")+1));
				}
				else {
					tmp_str = s;
					i = 1;
				}
					tmp_unit_exp tmp_var = null;
					boolean control = false;
					while(iter.hasNext()) {
						tmp_var = iter.next();
						if(tmp_var.uni.equals(tmp_str)) {control = true;break;}
					}
					if(control) {tmp_var.deg-=i;}
					else {tmp.add(new tmp_unit_exp("s",i));}
				
			}
			for(String s : s2_units) {
				if(s.contains("^^")) {
					tmp_str =s.substring(0, s.indexOf("^")) ;
					i = Integer.parseInt(s.substring(s.indexOf("^")+1));
				}
				else {
					tmp_str = s;
					i = 1;
				}
					tmp_unit_exp tmp_var = null;
					boolean control = false;
					while(iter.hasNext()) {
						tmp_var = iter.next();
						if(tmp_var.uni.equals(tmp_str)) {control = true;break;}
					}
					if(control) {tmp_var.deg-=i;}
					else {tmp.add(new tmp_unit_exp("s",i));}
			}
			String result_final="";
			for(tmp_unit_exp t : tmp) {
				if(t.deg != 0) {result_final += t.uni+"^"+t.deg+"*";}
			}
			result_final = result_final.substring(0,result_final.length()-1);
			return new Unit("UNDEFINED_VAR","result_final");
		}
	}
