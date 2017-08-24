/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.validator.cfg.scriptengine;

/**
 * An interface which should be implemented by factories that create {@link ScriptEvaluator}s
 * used to evaluate script expressions for {@link org.hibernate.validator.constraints.ScriptAssert}
 * and {@link org.hibernate.validator.constraints.ParameterScriptAssert} constraints. An instance of
 * such factory should be passed during bootstrapping a validator.
 *
 * @author Marko Bekhta
 */
public interface ScriptEvaluatorFactory {

	/**
	 * Retrieves a script executor {@link ScriptEvaluator} for the given language.
	 *
	 * @param languageName The name of a scripting language.
	 * @return A script executor for the given language. Never null.
	 *
	 * @throws javax.validation.ValidationException in case when no {@link ScriptEvaluator} was
	 * found for a given {@code languageName}.
	 */
	ScriptEvaluator getScriptEvaluatorByLanguageName(String languageName);
}
