import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@ RunWith(Suite.class)
@ Suite.SuiteClasses({
	ParameterizedAcupunctureAcupointsTest.class,
	ParameterizedChiefComplaintTest.class,
	ParameterizedDiagnosis6Test.class,
	ParameterizedDiagnosisTranslationPrinciplesTest.class,
	ParameterizedDiagnosisZungfuTest.class,
	ParameterizedTreatmentFormulaTest.class,
	ParameterizedTuinaChannelTest.class,
	ParameterizedTuinaLocationTest.class,
	ParameterizedTuinaManipulationTest.class,
})
public class JunitTestSuite {
}
