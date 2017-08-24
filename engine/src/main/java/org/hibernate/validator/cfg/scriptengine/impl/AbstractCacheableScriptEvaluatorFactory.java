/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.validator.cfg.scriptengine.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ValidationException;

import org.hibernate.validator.cfg.scriptengine.ScriptEvaluator;
import org.hibernate.validator.cfg.scriptengine.ScriptEvaluatorFactory;

/**
 * Basic cacheable factory responsible for the creation of {@link ScriptEvaluator}s. This
 * class is thread-safe. Caches {@code ScriptEvaluator} when they are requested.
 *
 * @author Gunnar Morling
 * @author Kevin Pollet &lt;kevin.pollet@serli.com&gt; (C) 2011 SERLI
 * @author Marko Bekhta
 */
public abstract class AbstractCacheableScriptEvaluatorFactory implements ScriptEvaluatorFactory {

	/**
	 * A cache of script executors (keyed by language name).
	 */
	private final ConcurrentMap<String, ScriptEvaluator> scriptExecutorCache = new ConcurrentHashMap<>();

	/**
	 * Retrieves a script executor for the given language.
	 *
	 * @param languageName The name of a scripting language as expected by {@link ScriptEngineManager#getEngineByName(String)}.
	 * @return A script executor for the given language. Never null.
	 *
	 * @throws ValidationException In case no compatible evaluator for the given language could be found.
	 */
	@Override
	public ScriptEvaluator getScriptEvaluatorByLanguageName(String languageName) {
		if ( !scriptExecutorCache.containsKey( languageName ) ) {

			ScriptEvaluator scriptExecutor = null;
			try {
				scriptExecutor = createNewScriptEvaluator( languageName );
			}
			catch (ScriptException e) {
				throw new ConstraintDeclarationException( "Wasn't able to find a script evaluator for '" + languageName + "' language.", e );
			}
			scriptExecutorCache.putIfAbsent( languageName, scriptExecutor );
		}

		return scriptExecutorCache.get( languageName );
	}

	/**
	 * Creates a new script executor for the given language.
	 *
	 * @param languageName A language name.
	 * @return A newly created script executor for the given language.
	 *
	 * @throws ScriptException In case no JSR 223 compatible engine for the given language could be found.
	 */
	protected abstract ScriptEvaluator createNewScriptEvaluator(String languageName) throws ScriptException;
}
