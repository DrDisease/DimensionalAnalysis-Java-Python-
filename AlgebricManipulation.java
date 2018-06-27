//import java.util.Scanner;

public class AlgebricManipulation {

	//Scanner sc = new Scanner(System.in);
	
	public class Variable{					// Support Type for algebric manipulation		
		private Unit un;
		private double value;
		public Unit getUnit() {return un;}
		public double getValue() {return value;}
		public Variable(Unit n, double v) {un=n;value=v;}
		}
		public Variable var_mult(Variable v1,Variable v2) {				// Multiplication of this*v1
			double new_value = v1.value * v2.value;
			Unit res = v1.un.Unit_Mult(v2.un);
			//res.setName(sc.next)
			return new Variable(res, new_value);						// May need to set the unit's name. Check the Unit Class
		}
		public Variable var_div(Variable v1,Variable v2) {				// Division of this/v1
			double new_value = v1.value / v2.value;
			Unit res = v1.un.Unit_Div(v2.un);
			//res.setName(sc.next)
			return new Variable(res, new_value);						//May need to set the unit's name. Check the Unit Class
		}
		public Variable var_add(Variable v1, Variable v2) {				//Adiition of this+v1
			if(!(v1.un.getName().equals(v2.getUnit().getName()))) {System.err.println("TYPE_MISMATCH");}
			double new_value = v1.value + v2.value;
			return new Variable(v1.un,new_value);
		}
		public Variable var_sub(Variable v1,Variable v2) {				// Subtraction of this-v1
			if(!(v1.un.getName().equals(v2.un.getName()))) {System.err.println("TYPE_MISMATCH");}
			double new_value = v1.value - v2.getValue();
			return new Variable(v1.un,new_value);
		}
		
	}

