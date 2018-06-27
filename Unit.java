import java.util.LinkedList;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

	public class Unit{																			// Unit Class Definition
		private class tmp_unit_exp{																// Secondary type to do operations->Parsed from the formula in the Unit data type
			String uni;																			// uni = reduced name of an unit (ex: meter = m)
			int deg;																			// deg =degree of unit (ex: m^2(meter squared) deg=2 )
			private tmp_unit_exp(String u,int e) {uni=u;deg=e;}									// Constructor
			}
		
		private static java.util.HashMap<String,String> UnitList = new HashMap<String,String>(); // static HashMap that stores every Unit created 
		
		private String name;					// name of the unit
		private String formula;					// formula
		
		public Unit(String n, String f) {												// Constructor
			
			this.name=n;this.formula=this.formatFormula(f);								// Formula is formated according to a specific pattern, so that every fomula is 	
			if (!UnitList.containsKey(this.formula)) {UnitList.put(formula, name);}		// displayed in the same pattern(as it is used as a key in the HashMap)
		}
		
///////////////////////////////////GETTERS///////////////////////////////////		
		
		public String getName() {return name;}
		public String getFormula() {return formula;}
		
		public void setName(String n) {this.name=n;}			//SETTER
		
		
		@Override												// EQUALS
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Unit other = (Unit) obj;
			if (formula == null) {
				if (other.formula != null)
					return false;
			} else if (!formula.equals(other.formula))
				return false;
			return true;
		}
		
		public static boolean CheckList_formula(String f) {				// Checks the HashMap to check if a certain Unit already exists
			if (UnitList.containsKey(f)){return true;}
			return false;
			}
		
		
		
		
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
		/////////////Formulas must come in Strings (s(^INT)?('*'(^INT)?)*)//////////////////////////////
		
		//[CODE_PARSING]
		
		private String formatFormula(String f) {											//Fromats formula according to a specific pattern
			
			
			LinkedList<tmp_unit_exp> tmp = new LinkedList<tmp_unit_exp>();		// LinkedList stores the unit and its degree
			
			String f_formed="";													// f_formed is our formatted string													
			String[] f_units = f.split("[*]");									// f_units is self descriptive
			
			
			int i;										// Variables for the for_each cycle 
			String tmp_str;								// ^^
			
			
			for(String s : f_units) {				// For_each cycle
				
				
				
				if(s.contains("^")) {									// if string contains '^', then the unit has a degree (aka: m^2)
					
					tmp_str =s.substring(0, s.indexOf("^")) ;				// substring tmp_str will "catch" the unit part of the string
					i = Integer.parseInt(s.substring(s.indexOf("^")+1));	// While i will be the parsed Integer part of the string.
				
				}
				else {
					tmp_str = s;										// If there is no ^ then there is no degree written, so, degree = 1
					i = 1;
				}
				
				tmp.add(new tmp_unit_exp(tmp_str,i));					// Puts a tmp_unit_exp in the Linked List, and begins a new for cycle
			}
				
			Collections.sort(tmp, new Comparator<tmp_unit_exp>() {		// Sorts the Linked List according to the unit name
				    @Override
				    public int compare(tmp_unit_exp o1, tmp_unit_exp o2) {
				    return Collator.getInstance().compare(o1.uni, o2.uni);
				    }});
			
				for(tmp_unit_exp t : tmp) {								// For each unit, adds to the String that will be returned
				f_formed += t.uni+"^"+t.deg+"*";
				}
				return f_formed;										// Returns formatted String
		}
		
		
		
		
		
		public Unit Unit_Mult(Unit n) {											//Multiplication of variables
			
			
			
	//////////FORMULA PARSING CODE	------> CHECK TAG [CODE_PARSING]		
			
			
			LinkedList<tmp_unit_exp> tmp = new LinkedList<tmp_unit_exp>();
			String s1 = n.getFormula();
			String s2 = this.formula;
			String[] s1_units = s1.split("[*]");
			String[] s2_units = s2.split("[*]");
			int i ;
			Iterator<tmp_unit_exp> iter = tmp.iterator();
			String tmp_str;
			
			
			
			
			for(String s : s1_units) {														//PARSES S1
				
				
				if(s.contains("^")) {											// Transforms s into a literal and a degree
					tmp_str =s.substring(0, s.indexOf("^")) ;
					i = Integer.parseInt(s.substring(s.indexOf("^")+1));
				}
				else {
					tmp_str = s;
					i = 1;
				}					
					tmp.add(new tmp_unit_exp("s",i));						// Puts a new (literal,degree) into the LinkedList
			}
			
			
			
			for(String s : s2_units) {														//PARSES S2
				
				
				if(s.contains("^^")) {										//transforms s into a literal and a degree
					tmp_str =s.substring(0, s.indexOf("^")) ;
					i = Integer.parseInt(s.substring(s.indexOf("^")+1));
				}
				else {
					tmp_str = s;
					i = 1;
				}
				
				
				
					tmp_unit_exp tmp_var = null;
					boolean control = false;
					
					while(iter.hasNext()) {										//Iterator iterates through the linked list in search for a the same literal
						tmp_var = iter.next();
						if(tmp_var.uni.equals(tmp_str)) {control = true;break;}
					}
					if(control) {tmp_var.deg+=i;}							//If the literal exists, degree is incremented
					else {tmp.add(new tmp_unit_exp("s",i));}				// If it doesn't a new (literal,degree) is put into the LinkedList
			}
			
			
			
			Collections.sort(tmp, new Comparator<tmp_unit_exp>() {				//Sorts the LinkedList
			     @Override
			     public int compare(tmp_unit_exp o1, tmp_unit_exp o2) {
			         return Collator.getInstance().compare(o1.uni, o2.uni);
			     }
			 });
			
			String result_final="";											//Creates the final String
			for(tmp_unit_exp t : tmp) {
			if(t.deg != 0) {result_final += t.uni+"^"+t.deg+"*";}			//If the degree==0, then we can ignore this literal
			}
			result_final = result_final.substring(0,result_final.length()-1);
			String var_name="";
			if(UnitList.containsKey(result_final)) {var_name=UnitList.get(result_final);}
			else {var_name="UNDEFINED TYPE";}
			return new Unit(var_name,result_final);				//Variable name is UNDEFINED_VAR. name must be changed after being created and must be put unto the variable HashMap
		}
		
		
		public Unit Unit_Div(Unit n) {										// Same as UnitMult, but instead of incrementing the degree, it decrements it
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
			
			 Collections.sort(tmp, new Comparator<tmp_unit_exp>() {
			     @Override
			     public int compare(tmp_unit_exp o1, tmp_unit_exp o2) {
			         return Collator.getInstance().compare(o1.uni, o2.uni);
			     }
			 });
			
			 String result_final="";											//Creates the final String
				for(tmp_unit_exp t : tmp) {
				if(t.deg != 0) {result_final += t.uni+"^"+t.deg+"*";}			//If the degree==0, then we can ignore this literal
				}
				result_final = result_final.substring(0,result_final.length()-1);
				String var_name="";
				if(UnitList.containsKey(result_final)) {var_name=UnitList.get(result_final);}
				else {var_name="UNDEFINED TYPE";}
				return new Unit(var_name,result_final);				//Variable name is UNDEFINED_VAR. name must be changed after being created and must be put unto the variable HashMap
			}
	}
