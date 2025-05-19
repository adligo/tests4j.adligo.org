# Copy to the src folder then run
# Run from the root tsts4j.adligo.org/src project folder after you have setup
echo $JAVA_HOME
echo $JAXB_HOME
xjc.sh org/adligo/tests4j/run/xml/coverage_v1_0.xsd 
xjc.sh org/adligo/tests4j/run/xml/params_v1_0.xsd
xjc.sh org/adligo/tests4j/run/xml/requirements_v1_0.xsd 
xjc.sh org/adligo/tests4j/run/xml/test_result_v1_0.xsd 
xjc.sh org/adligo/tests4j/run/xml/trial_result_v1_0.xsd 
xjc.sh org/adligo/tests4j/run/xml/trial_run_result_v1_0.xsd 
xjc.sh org/adligo/tests4j/run/xml/use_cases_v1_0.xsd 