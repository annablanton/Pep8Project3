package model;

import view.GUI;

/**
 * A class which represents a virtual CPU.
 * Used by the Machine class to execute instructions stored in memory.
 * @author Group 6:Anna Blanton, Walter Kagel, John Morton
 * @version 10/18/2020
 */
public class CPU {
	
	/**
	 * Register which stores the current instruction.
	 */
	private InstructionRegister instrReg;
	
	/**
	 * Register which points towards the address of the next instruction.
	 */
	private ProgramCounter progCounter;
	
	/**
	 * Accelerator register which is used for simple arithmetic tasks.
	 */
	private Register regA;
	
	/**
	 * Arithmetic and Logic unit for this CPU
	 */
	private ALU myALU;

	/**
	 * Needed to access input.
	 */
	private GUI pep8View;

	/**
	 * Used to tell the cpu if it is the first instruction and it needs to reset the program counter.
	 */
	private boolean isFirst;
	
	/**
	 * Initializes our CPU
	 */
	public CPU(GUI view) {
		instrReg = new InstructionRegister();
		progCounter = new ProgramCounter();
		regA = new Register();
		myALU = new ALU(view);
		pep8View = view;
	}

	/**
	 * The CPU reads and executes an instruction from memory based on the value of progCounter.
	 * @param m The memory that the CPU will be reading from.
	 */
	public boolean fetchExecute(Memory m) {
		if (isFirst) {
			progCounter.reset();
			isFirst = false;
		}
		return execute(m, decode(m));
	}

	/**
	 * Decodes an instruction and returns an integer representing it.
	 * @param m The memory that the CPU will be reading from.
	 * @return Integer representing the instruction stored in memory.
	 */
	private int decode(Memory m) {
		instrReg.setSpecifier(m.getDataAt(progCounter.getReg()));
		progCounter.offset((byte) 1);
		instrReg.load(fuseBytes(m.getDataAt(progCounter.getReg()),
				m.getDataAt((short) (progCounter.getReg() + 1))));
		return checkInstruction(instrReg.getSpecifier());
	}
	
	/**
	 * Helper method for decode method, uses byte data to determine the instruction.
	 * @param byte1 The first half of our instruction specifier.
	 * @return Returns an integer indicating the instruction.
	 */
	private int checkInstruction(byte byte1) {
		int rtnVal = 99;
		if (byte1 == 0) {
			//stop execution
			isFirst = true;
			rtnVal = 0;
		} else {
			if (byte1 == (byte) -64) {
				//load operand into register A
				//immediate addressing mode
				rtnVal = 1;
			} else if (byte1 == (byte) -63) {
				//load operand into register A
				//direct addressing mode
				rtnVal = 2;
			} else if (byte1 == (byte) -31) {
				//store the A register to the location in operand
				rtnVal = 3;
			} else if (byte1 == (byte) -128) {
				//Subtract the operand to the A register
				//immediate addressing mode
				rtnVal = 6;
			} else if (byte1 == (byte) -127) {
				//Subtract the operand to the A register
				//direct addressing mode
				rtnVal = 7;
			} else if (byte1 == (byte) 112) {
				//Add the operand to the A register
				//immediate addressing mode
				rtnVal = 4;
			} else if (byte1 == (byte) 113) {
				//Add the operand to the A register
				//direct addressing mode
				rtnVal = 5;
			} else if (byte1 == (byte) 73) {
				//Char input to location of operand
				rtnVal = 8;
			} else if (byte1 == (byte) 80) {
				//Char output from operand
				//immediate addressing mode
				rtnVal = 9;
			} else if (byte1 == (byte) 81) {
				//Char output from operand
				//direct addressing mode
				rtnVal = 10;
			} else if (byte1 == (byte) 0x90) { //bitwise AND (immediate)
				rtnVal = 11;
			} else if (byte1 == (byte) 0x91) { //bitwise AND (direct)
				rtnVal = 12;
			} else if (byte1 == (byte) 0xA0) { //bitwise OR (immediate)
				rtnVal = 13;
			} else if (byte1 == (byte) 0xA1) { //bitwise OR (direct)
				rtnVal = 14;
			} else if (byte1 == (byte) 0x22) { //ROR
				rtnVal = 15;
			} else if (byte1 == (byte) 0x20) { //ROL
				rtnVal = 16;
			} else if (byte1 == (byte) 0x1E) { //ASR
				rtnVal = 17;
			} else if (byte1 == (byte) 0x1C) { //ASL
				rtnVal = 18;
			} else if (byte1 == (byte) 0x18) {
				//Bitwise invert value in accumulator
				rtnVal = 19;
			} else if (byte1 == (byte) 0x1A) {
				//Negate value in accumulator
				rtnVal = 20;
			} else if (byte1 == (byte) 0x04) {
				//Branch Unconditional
				//Immediate Addressing Mode
				rtnVal = 21;
			} else if (byte1 == (byte) 0x05) {
				//Branch Unconditional
				//Direct Addressing Mode
				rtnVal = 22;
			} else if (byte1 == (byte) 0x06) {
				//Branch if Less Than or Equal To
				//Immediate Addressing Mode
				rtnVal = 23;
			} else if (byte1 == (byte) 0x07) {
				//Branch if Less Than or Equal To
				//Direct Addressing Mode
				rtnVal = 24;
			} else if (byte1 == (byte) 0x08) {
				//Branch if Less Than
				//Immediate Addressing Mode
				rtnVal = 25;
			} else if (byte1 == (byte) 0x09) {
				//Branch if Less Than
				//Direct Addressing Mode
				rtnVal = 26;
			} else if (byte1 == (byte) 0x0A) {
				//Branch if Equal To
				//Immediate Addressing Mode
				rtnVal = 27;
			} else if (byte1 == (byte) 0x0B) {
				//Branch if Equal To
				//Direct Addressing Mode
				rtnVal = 28;
			} else if (byte1 == (byte) 0x0C) {
				//Branch if Not Equal To
				//Immediate Addressing Mode
				rtnVal = 29;
			} else if (byte1 == (byte) 0x0D) {
				//Branch if Not Equal To
				//Direct Addressing Mode
				rtnVal = 30;
			} else if (byte1 == (byte) 0x0E) {
				//Branch if Greater Than or Equal To
				//Immediate Addressing Mode
				rtnVal = 31;
			} else if (byte1 == (byte) 0x0F) {
				//Branch if Greater Than or Equal To
				//Direct Addressing Mode
				rtnVal = 32;
			} else if (byte1 == (byte) 0x10) {
				//Branch if Greater Than
				//Immediate Addressing Mode
				rtnVal = 33;
			} else if (byte1 == (byte) 0x11) {
				//Branch if Greater Than
				//Direct Addressing Mode
				rtnVal = 34;
			} else if (byte1 == (byte) 0x12) {
				//Branch if V Flag == true
				//Immediate Addressing Mode
				rtnVal = 35;
			} else if (byte1 == (byte) 0x13) {
				//Branch if V Flag == true
				//Direct Addressing Mode
				rtnVal = 36;
			} else if (byte1 == (byte) 0x14) {
				//Branch if C Flag == true
				//Immediate Addressing Mode
				rtnVal = 37;
			} else if (byte1 == (byte) 0x15) {
				//Branch if C Flag == true
				//Direct Addressing Mode
				rtnVal = 38;
			}
		}
		
		if (rtnVal == 99) {
			rtnVal = 99;
			System.out.println("Error: Unknown Opcode!");
			System.out.println("Opcode: " + byte1);
		}
		return rtnVal;
	}

	/**
	 * Executes the instruction stored in memory.
	 * @param m
	 * @param instrType
	 * @return
	 */
	private boolean execute(Memory m, int instrType) {
		boolean end = false;
		if (instrType == 0) {
			//stop execution
			end = true;
		} else {
			try {
//				byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
//				byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
//				progCounter.offset((byte) 2);
				if (instrType == 1) {
					//load operand into register A
					//immediate addressing mode
					byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
					byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
					progCounter.offset((byte) 2);
					short fuse = fuseBytes(operSpec1, operSpec2);
					regA.load(fuse);
					
				} else if (instrType == 2) {
					//load operand into register A
					//direct addressing mode
					byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
					byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
					progCounter.offset((byte) 2);
					short fuse = this.calculateDirectAddress(operSpec1, operSpec2);
					byte retrieved = m.getDataAt(fuse);
					byte retrievedN = m.getDataAt((short) (fuse + 1));
					short retr = fuseBytes(retrieved, retrievedN);
					regA.load(retr);
						
				} else if (instrType == 3) {
					//store the A register to the location in operand
					byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
					byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
					progCounter.offset((byte) 2);
					short whole = regA.getReg();
					boolean[] wholeBool = this.toBoolArray(whole);
					boolean[] half1 = new boolean[8];
					boolean[] half2 = new boolean[8];
					for (int i = 0; i < half1.length; i++) {
						half1[i] = wholeBool[i];
					}
					for (int i = 0; i < half2.length; i++) {
						half2[i] = wholeBool[i + 8];
					}
					byte sHalf1 = this.toByte(half1);
					byte sHalf2 = this.toByte(half2);
					short fuse = this.calculateDirectAddress(operSpec1, operSpec2);
					m.store(fuse, sHalf1);
					m.store((short) (fuse + 1), sHalf2);
					
				} else if (instrType == 4) {
					//Add the operand to the A register
					//immediate addressing mode
					byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
					byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
					progCounter.offset((byte) 2);
					short fuse = this.fuseBytes(operSpec1, operSpec2);
					regA.load(myALU.add(fuse, regA.getReg()));
						
				} else if (instrType == 5) {
					//Add the operand to the A register
					//direct addressing mode
					byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
					byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
					progCounter.offset((byte) 2);
					short address = this.calculateDirectAddress(operSpec1, operSpec2);
			        short addressR = (short) (address + 1);
			        byte addressVal = m.getDataAt(address);
			        byte addressRVal = m.getDataAt(addressR);
			        short fuse = this.fuseBytes(addressVal, addressRVal);
					regA.load(myALU.add(fuse, regA.getReg()));
						
				} else if (instrType == 6) {
					//Subtract the operand to the A register
					//immediate addressing mode
					byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
					byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
					progCounter.offset((byte) 2);
					short fuse = this.fuseBytes(operSpec1, operSpec2);
					regA.load(myALU.subtract(regA.getReg(), fuse));
					
				} else if (instrType == 7) {
					//Subtract the operand to the A register
					//direct addressing mode
					byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
					byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
					progCounter.offset((byte) 2);
					short address = this.calculateDirectAddress(operSpec1, operSpec2);
			        short addressR = (short) (address + 1);
			        byte addressVal = m.getDataAt(address);
			        byte addressRVal = m.getDataAt(addressR);
			        short fuse = this.fuseBytes(addressVal, addressRVal);
					regA.load(myALU.subtract(regA.getReg(), fuse));
					
				} else if (instrType == 8) {
					//Char input to location of operand
					byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
					byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
					progCounter.offset((byte) 2);
					byte scannedInput = (byte) pep8View.getBatchInput();
					short fuse = fuseBytes(operSpec1, operSpec2);
					m.store(fuse, scannedInput);

				} else if (instrType == 9) {
					//Char output from operand
					//immediate addressing mode
					byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
					byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
					progCounter.offset((byte) 2);
					short fuse = this.fuseBytes(operSpec1, operSpec2);
					char out = (char) fuse;
					pep8View.output(out);
				} else if (instrType == 10) {
					//Char output from operand
					//direct addressing mode
					byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
					byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
					progCounter.offset((byte) 2);
					short fuse = this.calculateDirectAddress(operSpec1, operSpec2);
					char out = (char) m.getDataAt(fuse);
					pep8View.output(out);
				} else if (instrType == 19) {
					//Bitwise invert the value stored in the accumulator
					regA.load(myALU.invert(regA.getReg()));
				}  else if (instrType == 11) {
					//Bitwise AND (immediate)
					byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
					byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
					progCounter.offset((byte) 2);
					short fuse = this.fuseBytes(operSpec1, operSpec2);
					regA.load(myALU.and(regA, fuse));
				} else if (instrType == 12) {
					byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
					byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
					progCounter.offset((byte) 2);
					short address = this.calculateDirectAddress(operSpec1, operSpec2);
					regA.load(myALU.and(regA, fuseBytes(m.getDataAt(address), m.getDataAt((short) (address+1)))));
				} else if (instrType == 13) {
					//bitwise OR (immediate)
					byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
					byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
					progCounter.offset((byte) 2);
					short fuse = this.fuseBytes(operSpec1, operSpec2);
					regA.load(myALU.or(regA, fuse));
				} else if (instrType == 14) {
					//bitwise OR (direct)
					byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
					byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
					progCounter.offset((byte) 2);
					short address = this.calculateDirectAddress(operSpec1, operSpec2);
					regA.load(myALU.or(regA, fuseBytes(m.getDataAt(address), m.getDataAt((short) (address+1)))));
				} else if (instrType == 15) { //ROR
					regA.load(myALU.rotateRight(regA));
					progCounter.offset((byte) 2);
				} else if (instrType == 16) { //ROL
					regA.load(myALU.rotateLeft(regA));
					progCounter.offset((byte) 2);
				} else if (instrType == 17) { //ASR
					regA.load(myALU.arithShiftRight(regA));
					progCounter.offset((byte) 2);
				} else if (instrType == 18) { //ASL
					regA.load(myALU.arithShiftLeft(regA));
					progCounter.offset((byte) 2);
				} else if (instrType == 19) {
					//Bitwise invert the value stored in the accumulator
					regA.load(myALU.invert(regA.getReg()));
					progCounter.offset((byte) 2);
				} else if (instrType == 20) {
					//Negate the value stored in the accumulator
					regA.load(myALU.negate(regA.getReg()));
					progCounter.offset((byte) 2);
				} else if (instrType == 21) {
					//Branch Unconditional
					//Immediate Addressing Mode
					byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
					byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
					short fuse = fuseBytes(operSpec1, operSpec2);
					progCounter.setPC(fuse);
				} else if (instrType == 22) {
					//Branch Unconditional
					//Direct Addressing Mode
					byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
					byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
					short fuse = this.calculateDirectAddress(operSpec1, operSpec2);
					byte retrieved = m.getDataAt(fuse);
					byte retrievedN = m.getDataAt((short) (fuse + 1));
					short retr = fuseBytes(retrieved, retrievedN);
					progCounter.setPC(retr);
				} else if (instrType == 23) {
					//Branch if RegA Less Than or Equal To Zero
					//Immediate Addressing Mode
					if (regA.getReg() <= 0) {
						byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
						byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
						short fuse = fuseBytes(operSpec1, operSpec2);
						progCounter.setPC(fuse);
					} else {
						progCounter.offset((byte) 2);
					}
				} else if (instrType == 24) {
					//Branch if RegA Less Than or Equal To Zero
					//Direct Addressing Mode
					if (regA.getReg() <= 0) {
						byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
						byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
						short fuse = this.calculateDirectAddress(operSpec1, operSpec2);
						byte retrieved = m.getDataAt(fuse);
						byte retrievedN = m.getDataAt((short) (fuse + 1));
						short retr = fuseBytes(retrieved, retrievedN);
						progCounter.setPC(retr);
					} else {
						progCounter.offset((byte) 2);
					}
				} else if (instrType == 25) {
					//Branch if RegA Less Than Zero
					//Immediate Addressing Mode
					if (regA.getReg() < 0) {
						byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
						byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
						short fuse = fuseBytes(operSpec1, operSpec2);
						progCounter.setPC(fuse);
					} else {
						progCounter.offset((byte) 2);
					}
				} else if (instrType == 26) {
					//Branch if RegA Less Than Zero
					//Direct Addressing Mode
					if (regA.getReg() <= 0) {
						byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
						byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
						short fuse = this.calculateDirectAddress(operSpec1, operSpec2);
						byte retrieved = m.getDataAt(fuse);
						byte retrievedN = m.getDataAt((short) (fuse + 1));
						short retr = fuseBytes(retrieved, retrievedN);
						progCounter.setPC(retr);
					} else {
						progCounter.offset((byte) 2);
					}
				} else if (instrType == 27) {
					//Branch if RegA Equal To Zero
					//Immediate Addressing Mode
					if (regA.getReg() == 0) {
						byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
						byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
						short fuse = fuseBytes(operSpec1, operSpec2);
						progCounter.setPC(fuse);
					} else {
						progCounter.offset((byte) 2);
					}
				} else if (instrType == 28) {
					//Branch if RegA Equal To Zero
					//Direct Addressing Mode
					if (regA.getReg() <= 0) {
						byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
						byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
						short fuse = this.calculateDirectAddress(operSpec1, operSpec2);
						byte retrieved = m.getDataAt(fuse);
						byte retrievedN = m.getDataAt((short) (fuse + 1));
						short retr = fuseBytes(retrieved, retrievedN);
						progCounter.setPC(retr);
					} else {
						progCounter.offset((byte) 2);
					}
				} else if (instrType == 29) {
					//Branch if RegA Not Equal To Zero
					//Immediate Addressing Mode
					if (regA.getReg() != 0) {
						byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
						byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
						short fuse = fuseBytes(operSpec1, operSpec2);
						progCounter.setPC(fuse);
					} else {
						progCounter.offset((byte) 2);
					}
				} else if (instrType == 30) {
					//Branch if RegA Not Equal To Zero
					//Direct Addressing Mode
					if (regA.getReg() <= 0) {
						byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
						byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
						short fuse = this.calculateDirectAddress(operSpec1, operSpec2);
						byte retrieved = m.getDataAt(fuse);
						byte retrievedN = m.getDataAt((short) (fuse + 1));
						short retr = fuseBytes(retrieved, retrievedN);
						progCounter.setPC(retr);
					} else {
						progCounter.offset((byte) 2);
					}
				} else if (instrType == 31) {
					//Branch if RegA Greater Than or Equal To Zero
					//Immediate Addressing Mode
					if (regA.getReg() >= 0) {
						byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
						byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
						short fuse = fuseBytes(operSpec1, operSpec2);
						progCounter.setPC(fuse);
					} else {
						progCounter.offset((byte) 2);
					}
				} else if (instrType == 32) {
					//Branch if RegA Greater Than or Equal To Zero
					//Direct Addressing Mode
					if (regA.getReg() <= 0) {
						byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
						byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
						short fuse = this.calculateDirectAddress(operSpec1, operSpec2);
						byte retrieved = m.getDataAt(fuse);
						byte retrievedN = m.getDataAt((short) (fuse + 1));
						short retr = fuseBytes(retrieved, retrievedN);
						progCounter.setPC(retr);
					} else {
						progCounter.offset((byte) 2);
					}
				} else if (instrType == 33) {
					//Branch if RegA Greater Than Zero
					//Immediate Addressing Mode
					if (regA.getReg() > 0) {
						byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
						byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
						short fuse = fuseBytes(operSpec1, operSpec2);
						progCounter.setPC(fuse);
					} else {
						progCounter.offset((byte) 2);
					}
				} else if (instrType == 34) {
					//Branch if RegA Greater Than Zero
					//Direct Addressing Mode
					if (regA.getReg() <= 0) {
						byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
						byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
						short fuse = this.calculateDirectAddress(operSpec1, operSpec2);
						byte retrieved = m.getDataAt(fuse);
						byte retrievedN = m.getDataAt((short) (fuse + 1));
						short retr = fuseBytes(retrieved, retrievedN);
						progCounter.setPC(retr);
					} else {
						progCounter.offset((byte) 2);
					}
				} else if (instrType == 35) {
					//Branch if V Flag == true
					//Immediate Addressing Mode
					if (myALU.vFlagIsSet()) {
						byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
						byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
						short fuse = fuseBytes(operSpec1, operSpec2);
						progCounter.setPC(fuse);
					} else {
						progCounter.offset((byte) 2);
					}
				} else if (instrType == 36) {
					//Branch if V Flag == true
					//Direct Addressing Mode
					if (myALU.vFlagIsSet()) {
						byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
						byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
						short fuse = this.calculateDirectAddress(operSpec1, operSpec2);
						byte retrieved = m.getDataAt(fuse);
						byte retrievedN = m.getDataAt((short) (fuse + 1));
						short retr = fuseBytes(retrieved, retrievedN);
						progCounter.setPC(retr);
					} else {
						progCounter.offset((byte) 2);
					}
				} else if (instrType == 37) {
					//Branch if C Flag == true
					//Immediate Addressing Mode
					if (myALU.cFlagIsSet()) {
						byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
						byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
						short fuse = fuseBytes(operSpec1, operSpec2);
						progCounter.setPC(fuse);
					} else {
						progCounter.offset((byte) 2);
					}
				} else if (instrType == 38) {
					//Branch if C Flag == true
					//Direct Addressing Mode
					if (myALU.cFlagIsSet()) {
						byte operSpec1 = (byte) (((instrReg.getReg() & 0xFF00)) >> 8);
						byte operSpec2 = (byte) (instrReg.getReg() & 0xFF);
						short fuse = this.calculateDirectAddress(operSpec1, operSpec2);
						byte retrieved = m.getDataAt(fuse);
						byte retrievedN = m.getDataAt((short) (fuse + 1));
						short retr = fuseBytes(retrieved, retrievedN);
						progCounter.setPC(retr);
					} else {
						progCounter.offset((byte) 2);
					}
				}
			} catch (Exception E) {
				System.out.println("Error in Execution!");
			}
			
		}
		return end;
	}
	
	/**
	 * Helper function used to calculate the direct address.
	 * @param b1 Byte from the first operand.
	 * @param b2 Byte from the second operand.
	 * @return Short containing the desired address.
	 */
	private short calculateDirectAddress(byte b1, byte b2) {
		boolean[] oper1Array = this.toBoolArray(b1);
		boolean[] oper2Array = this.toBoolArray(b2);
		boolean[] fuseArray = new boolean[16];
		for (int i = 0; i < oper1Array.length; i++) {
			fuseArray[i] = oper1Array[i];
		}
		for (int i = 0; i < oper2Array.length; i++) {
			fuseArray[8 + i] = oper2Array[i];
		}
		short fuse = this.toShort(fuseArray);
		return fuse;
	}
	
	/**
	 * A helper function which converts a byte into an 8 bit boolean array.
	 * @param x The byte that we will manipulate.
	 * @return A boolean array equivalent to our byte value.
	 */
	private boolean[] toBoolArray(byte x) {
		boolean[] rtnArray = new boolean[8];
		byte twoPow[] = {64, 32, 16, 8, 4, 2, 1};
		for (int i = 0; i < rtnArray.length; i++) {
			if (i == 0) {
				if (x < 0) {
					rtnArray[i] = true;
					x = (byte) (x - 128);
				}
			} else {
				if (x >= twoPow[i - 1]) {
					rtnArray[i] = true;
					x-= twoPow[i - 1];
				} else {
					rtnArray[i] = false;
				}
			}
		}
		return rtnArray;
	}
	
	/**
	 * A helper method which converts a boolean array to a short.
	 * @param boolArray The boolean array that we wish to convert.
	 * @return A short value representing the input boolean array.
	 */
	private short toShort(boolean[] boolArray) {
		short rtnShort = 0;
		short twoPow[] = {16384, 8192, 4096, 2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1};
		for (int i = 0; i < boolArray.length; i++) {
			if (i == 0) {
				if (boolArray[i]) {
					rtnShort = (short) -32768;
				}
			} else {
				if (boolArray[i]) {
					rtnShort += twoPow[i - 1];
				}
			}
		}
		return rtnShort;
	}
	
	/**
	 * Helper function, Combine 2 bytes into a short
	 * @param b1 The first byte
	 * @param b2 The second byte
	 * @return Short resulting from placing these bytes back to back.
	 */
	private short fuseBytes(byte b1, byte b2) {
		boolean[] oper1Array = this.toBoolArray(b1);
		boolean[] oper2Array = this.toBoolArray(b2);
		boolean[] fuseArray = new boolean[16];
		for (int i = 0; i < oper1Array.length; i++) {
			fuseArray[i] = oper1Array[i];
		}
		for (int i = 0; i < oper2Array.length; i++) {
			fuseArray[8 + i] = oper2Array[i];
		}
		return this.toShort(fuseArray);
	}
	
	/**
	 * A helper function which converts a short into a 16 bit boolean array.
	 * @param x The short that we will manipulate.
	 * @return A boolean array equivalent to our short value.
	 */
	private boolean[] toBoolArray(short x) {
		boolean[] rtnArray = new boolean[16];
		short[] twoPow = {16384, 8192, 4096, 2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1};
			for (int i = 0; i < rtnArray.length; i++) {
				if (i == 0) {
					if (x < 0) {
						rtnArray[0] = true;
						x = (short) (x - 32768);
					} else {
						rtnArray[0] = false;
					}
				} else {
					if (x >= twoPow[i - 1]) {
						rtnArray[i] = true;
						x-= twoPow[i - 1];
					} else {
						rtnArray[i] = false;
					}
				}
			}
		return rtnArray;
	}
	
	/**
	 * A helper method which converts a boolean array to a short.
	 * @param boolArray The boolean array that we wish to convert.
	 * @return A short value representing the input boolean array.
	 */
	private byte toByte(boolean[] boolArray) {
		byte rtnByte = 0;
		byte[] twoPow = {64, 32, 16, 8, 4, 2, 1};
		for (int i = 0; i < boolArray.length; i++) {
			if (i == 0) {
				if (boolArray[i]) {
					rtnByte = (byte) -128;
				}
			} else {
				if (boolArray[i]) {
					rtnByte += twoPow[i - 1];
				}
			}
		}
		return rtnByte;
	}

	/**
	 * Method that returns the values in all of the cpu registers.
	 * @return short[] {InstRegSpec, InstrRegOperand, ProgCountReg, AccumReg}
	 */
	public short[] getRegisters() {
		return new short[] {instrReg.getSpecifier(), instrReg.getReg(),
				progCounter.getReg(), regA.getReg()};
	}

}
