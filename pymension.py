class unit:


	units = {'':'COONSTANT'}							# Static Dictionary that saves every unit created
														# No Unit means it's a constant

	def __init__(self, name, form):				#Constructor
		
		fform=self.formated_formula(form)		#formatting formula according to Standard
		
		if (not(self.checkExistsFormula(self,fform))):	#if formula doesn't exist in units	
			self.units[form]=name 						#puts formula into unit with respective name
		
		else:
			if self.units[fform] != name:				#if unit already exists with different name, Error message i printed
				print"\nERROR: SAME FORMULA GENERATES MULTIPLE UNITS\nVARIABLE NOT CREATED"
				exit(0)									#Exits the program 
		
		self.f=fform			#thi object's formula = formated formula
		self.name=name          #object's name = name


	def checkExistsFormula(self,f):			#checks if formula exists in dictionary
		return f in self.units.keys()

	def checkExistsUnit(self,u):			#checs if unit exists in dictionary
		return u in self.untis.values()

	def formated_formula(self, form):		#formats formulas
		if form=='':
			return form
		formatted = ''
		tmp = str(form)
		if tmp.find("*")!=-1:
			this_units = tmp.split('*')
			this_units.sort()
			for member in this_units:
				formatted = formatted+member+'*'
			else:
				formatted = form
		return formatted




	def unit_mult(self,op1,op2):        ## op1 and op2 must be formulas
		

		op_units_dic={}					#dictionary that will store subUnits and their respective degrees
		

		if op1.f.find('*')!=-1:			#This block will parse through the 1st string and put 
			op1_units=op1.f.split('*')	#the subunits and their degrees in the dictionary
			for members in op1_units:
				if(members.find('^')!=-1):
					tmp_list = members.split('^')
					op_units_dic[tmp_list[0]]=int(tmp_list[1])
				else:
					op_units_dic[members] = 1
		else:
			op_units_dic[op1.f]=1

		

		if op2.f.find('*')!=-1:					#Does the same as the 1st block, but if a subunit alrady exists
			op2_units=op2.f.split('*')			#subtracts the degree from subunit's already defined degree
			for members in op2_units:
				if(members.find('^'))!=-1:
					tmp_list=members.split('^')
					if(tmp_list[0] in op_units_dic.keys()):
						op_units_dic[tmp_list[0]]=op_units_dic[tmp_list[0]]+tmp_list[1]
					else:
						op_units_dic[tmp_list[0]]=tmp_list[1]
				else:
					if (tmp_list[0] in op_units_dic.keys()):
						op_units_dic[tmp_list[0]] = op_units_dic[tmp_list[0]] + 1
					else:
						op_units_dic[tmp_list[0]] = 1

		

		s=''
		for key, value in op_units_dic.iteritems():	#iters over the dictionary and forms the new String formula
			if value!=0:
				s +key+'^'+value+'*'
				s = s[:-1]
		return self.formated_formula(s)			#returns the new formated formula


	def unit_div(self,op1,op2):        ## op1 and op2 must be formulas
		


		op_units_dic={}				#dictionary that will store subUnits and their respective degrees
		

		if op1.f.find('*')!=-1:				#This block will parse through the 1st string and put 
			op1_units=op1.f.split('*')		#the subunits and their degrees in the dictionary
			for members in op1_units:
				if(members.find('^')!=-1):
					tmp_list = members.split('^')
					op_units_dic[tmp_list[0]]=int(tmp_list[1])
				else:
					op_units_dic[members] = 1
		else:
			op_units_dic[op1.f]=1

		

		if op2.f.find('*')!=-1:				#Does the same as the 1st block, but if a subunit alrady exists
			op2_units=op2.f.split('*')		#subtracts the degree from subunit's already defined degree
			for members in op2_units:
				if(members.find('^'))!=-1:
					tmp_list=members.split('^')
					if(tmp_list[0] in op_units_dic.keys()):
						op_units_dic[tmp_list[0]]=op_units_dic[tmp_list[0]]-tmp_list[1]
					else:
						op_units_dic[tmp_list[0]]=-tmp_list[1]
				else:
					if (tmp_list[0] in op_units_dic.keys()):
						op_units_dic[tmp_list[0]] = op_units_dic[tmp_list[0]] - 1
					else:
						op_units_dic[tmp_list[0]] = -1

		s=''
		

		for key, value in op_units_dic.iteritems():	#iters over the dictionary and forms the new String formula
			if value!=0: 
				s +key+'^'+value+'*'
				s = s[:-1]
		
		return self.formated_formula(s)			#returns the new formated formula

	

	def getName(self,fformated):														#gets name of unit defined by formula. 
		if(self.checkExistFormula()):													#if name hasn't yet been defined, returns formated formula
			return self.units[fformated]
		else:
			print "\nResult variable still has not been defined \n Variable: " + fformated # control message
			return fformated


	def getNewUnit(self,unit_name):
		for key, value in units.iteritems():	#iters over the dictionary and forms the new String formula
			if value==unit_name:
				a = unit(value,key)
				return a
		printf("\nThat unit doesn't exist")		



class variable:

	def __init__(self,value,name,formula):			#Constructor 
		self.val=value 								#int or float
		self.measure=unit(name,formula)				#Measure is object of type unit

				
	
	def variable_add(self,op1,op2):					#adds 2 objects of class variable
													#if objects aren't from class variable, raises exception
		if(op1.measure.f!=op2.measure.f):			#Checks if ormulas are the same
			print"\nSum between different units is not possible"
		else:
			return variable(op1.val+op2.val,unit(op1.measure.name,op1.measure.f)) #return variable object


	def variable_sub(self,op1,op2):				#subtracts 2 objects of class variable
												#if objects aren't from class variable, raises exception
		if(op1.measure.f!=op2.measure.f):		#checks if units are from same class
			print"\nSubtraction between different units is not possible"
		else:
			return variable(op1.val-op2.val,unit(op1.measure.name,op1.measure.f))


	def variable_mult(selfself,op1,op2):													#multplies 2 objects of class variable
		unit_formula=unit.unit_mult(op1.measure.f,op2.measure.f)							#new formula deduction
		return variable(op1.val*op2.val,unit(unit.getName(unit_formula),unit_formula))		#multiplies each variable's value and assigns to the return variable the new formula
																							#and a new unit if already defined. If not defined, unit name = formula
	
	def variable_div(selfself,op1,op2):													#Divides 2 objects of class variable
		if op2.val == 0 :																	#checks if op2.value == 0
			print "\nERROR: IMPOSSIBLE TO DIVIDE BY ZERO"										#Dividing by Zero is not possible
			#exit(0)																		#Exits execution		
		unit_formula=unit.unit_div(op1.measure.f,op2.measure.f)								#new formula deduction
		return variable(op1.val/op2.val,unit(unit.getName(unit_formula),unit_formula))		#Divides both operands values and assigns the retuen value the new formula
																							#and a new unit if already defined. If not defined, unit name = formula