package org.adligo.tests4j.models.shared.coverage;
/**
 * Notes on line coverage segment
 * 
 * Ok branches seem to fall into two cases
 * loops that never occurred because the loop was empty
 * which ideal would result in
 * <green> for (</green><yellow>String p: </yellow><green> list) {</green>
 * which illuminates the empty list case
 * 
 * and if statements
 * 
 * if (true == YourTrue && false == YourFalse) {
 * 4 branches 10 cases (A, B, C, D, A&B, A&C, A&D, B&C, B&D, C&D)
 * A everything true
 * B YourTrue true YourFalse false
 * C YourTrue false YourFalse true
 * D everything false
 * 
 * if (true == YourTrue && false == YourFalse) {
 * case A
 * <green>if (</green><yellow>true == YourTrue</yellow><red>&&</red><yellow> false == YourFalse</yellow><green>) {<green>
 * tool tip over green '	2/4 branches covered.'
 * tool tip over first yellow '	1/4 branches not covered 
 * 								<green>(true == YourTrue) == true</green> 
 * 								but not 
 * 								<red>(true == YourTrue) == false</red>'
 * tool tip over red '2/4 branches not covered.'
 * tool tip over second yellow '	1/4 branches not covered 
 * 								<green>(false == YourFalse) == true</green> 
 * 								but not 
 * 								<red>(false == YourFalse) == false</red>'
 * 
 * 
 * if (true == YourTrue && false == YourFalse) {
 * case B
 * <green>if (</green><yellow>true == YourTrue</yellow><red>&&</red><yellow> false == YourFalse</yellow><green>) {</green>
 * tool tip over green '	2/4 branches covered.'
 * tool tip over first yellow '	1/4 branches not covered 
 * 								<green>(true == YourTrue) == true</green> 
 * 								but not 
 * 								<red>(true == YourTrue) == false</red>'
 * tool tip over red '2/4 branches not covered.'
 * tool tip over second yellow '	1/4 branches not covered 
 * 								<green>(false == YourFalse) == false</green> 
 * 								but not 
 * 								<red>(false == YourFalse) == true</red>'
 * 
 * if (true == YourTrue && false == YourFalse) {
 * case C&D
 * <green>if (</green><yellow>true == YourTrue</yellow><red>&&</red><green> false == YourFalse) {</green>
 * tool tip over green '	3/4 branches covered.'
 * tool tip over first yellow '	1/4 branches not covered 
 * 								<green>(true == YourTrue) == false</green> 
 * 								but not 
 * 								<red>(true == YourTrue) == true</red>'
 * tool tip over red '1/4 branches not covered.'
 * 
 * 
 * and
 * if (true == YourTrue || false == YourFalse) {
 * 4 branches 10 cases (A, B, C, D, A&B, A&C, A&D, B&C, B&D, C&D)
 * A everything true
 * B YourTrue true YourFalse false
 * C YourTrue false YourFalse false
 * D everything false
 * 
 * 
 * if (true == YourTrue || false == YourFalse) {
 * case A
 * <green>if (</green><yellow>true == YourTrue</yellow><green-yellow>||</green-yellow><yellow> false == YourFalse</yellow><green>) {<green>
 * tool tip over green '	2/4 branches covered.'
 * tool tip over first yellow '	1/4 branches not covered 
 * 								<green>(true == YourTrue) == true</green> 
 * 								but not 
 * 								<red>(true == YourTrue) == false</red>'
 * tool tip over green-yellow '2/4 branches covered.'
 * tool tip over second yellow '	1/4 branches not covered 
 * 								<green>(false == YourFalse) == true</green> 
 * 								but not 
 * 								<red>(false == YourFalse) == false</red>'
 * 
 *  if (true == YourTrue || false == YourFalse) {
 * case B
 * <green>if (</green><yellow>true == YourTrue</yellow><green-yellow>||</green-yellow><yellow> false == YourFalse</yellow><green>) {</green>
 * tool tip over green '	2/4 branches covered.'
 * tool tip over first yellow '	1/4 branches not covered 
 * 								<green>(true == YourTrue) == true</green> 
 * 								but not 
 * 								<red>(true == YourTrue) == false</red>'
 * tool tip over green-yellow '2/4 branches covered.'
 * tool tip over second yellow '	1/4 branches not covered 
 * 								<green>(false == YourFalse) == false</green> 
 * 								but not 
 * 								<red>(false == YourFalse) == true</red>'
 * 
 * if (true == YourTrue || false == YourFalse) {
 * case C&D
 * <green>if (</green><yellow>true == YourTrue</yellow><green-yellow>||</green-yellow><green> false == YourFalse) {</green>
 * tool tip over green '	3/4 branches covered.'
 * tool tip over first yellow '	1/4 branches not covered 
 * 								<green>(true == YourTrue) == false</green> 
 * 								but not 
 * 								<red>(true == YourTrue) == true</red>'
 * tool tip over red '1/4 branches not covered.'
 * 
 * and similar syntax highlighting for ternary statements
 * int result;
 * result = (true == YourTrue || false == YourFalse) ? value1 : value2;
 * <green>result = (<green><yellow>true == YourTrue </yellow><red>&&</red><yellow> false == YourFalse</yellow><green>) ? value1 : value2;</green>
 * 
 * So assuming you could use something like jmockit to get the 
 * branches in order (two per comparison) and have a way to determine
 * which of the pair pertains to true and which to false.  Where a comparison 
 * is something between a comparison operator, and a comparison operator
 * would one of the following;
 * conditional operators from;
 * http://docs.oracle.com/javase/tutorial/java/nutsandbolts/op2.html
 * 
 * or 
 * @author scott
 *
 */
public interface I_LineCoverageSegment {
	/**
	 * if this section of the line was covered as follows;
	 * true if it was covered.
	 * false if it was NOT covered.
	 * null if it was partially covered,
	 *    note if it was partially covered
	 *    the characters containing the start and end
	 *    
	 * @return
	 */
	public Boolean getCovered();
	
	/**
	 * note most impls of this will return between
	 * 0-255 for a unsigned byte
	 * @see Tiny
	 * @return
	 */
	public short getStart();
	/**
	 * note most impls of this will return between
	 * 0-255 for a unsigned byte
	 * @see Tiny
	 * 
	 * if this returns more than 255 and the
	 * actual source line goes to 226 or so
	 * just extend this line segment to the end.
	 * 
	 * @return
	 */
	public short getEnd();
	
}
