/*
 * Tanaguru - Automated webpage assessment
 * Copyright (C) 2008-2016  Tanaguru.org
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact us by mail: tanaguru AT tanaguru DOT org
 */
package org.tanaguru.rules.rgaa32016;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.tanaguru.entity.audit.TestSolution;
import org.tanaguru.ruleimplementation.AbstractMarkerPageRuleImplementation;
import org.tanaguru.rules.elementchecker.element.ChildElementPresenceChecker;
import org.tanaguru.rules.elementselector.SimpleElementSelector;
import org.tanaguru.rules.keystore.HtmlElementStore;
import static org.tanaguru.rules.keystore.HtmlElementStore.TABLE_ELEMENT;
import static org.tanaguru.rules.keystore.MarkerStore.DATA_TABLE_MARKER;
import static org.tanaguru.rules.keystore.MarkerStore.COMPLEX_TABLE_MARKER;
import static org.tanaguru.rules.keystore.MarkerStore.PRESENTATION_TABLE_MARKER;
import static org.tanaguru.rules.keystore.RemarkMessageStore.*;

/**
 * Implementation of the rule 5.4.1 of the referential Rgaa 3-2016.
 * <br/>
 * For more details about the implementation, refer to <a href="http://tanaguru-rules-rgaa3.readthedocs.org/en/latest/Rule-5-4-1">the rule 5.4.1 design page.</a>
 * @see <a href="http://references.modernisation.gouv.fr/referentiel-technique-0#test-5-4-1"> 5.4.1 rule specification</a>
 *
 */
public class Rgaa32016Rule050401 extends AbstractMarkerPageRuleImplementation {

    /**
     * Default constructor
     */
    public Rgaa32016Rule050401() {
        super(
                new SimpleElementSelector(TABLE_ELEMENT), 

                // the data tables are part of the scope
                new String[]{DATA_TABLE_MARKER},

                // the presentation and complex tables are not part of the scope
                new String[]{PRESENTATION_TABLE_MARKER, COMPLEX_TABLE_MARKER},

                // checker for elements identified by marker
                new ChildElementPresenceChecker(
                    HtmlElementStore.CAPTION_ELEMENT, 
                    1,
                    // passed when child element is found
                    new ImmutablePair(TestSolution.PASSED, ""),
                    // failed when child element is not found
                    new ImmutablePair(TestSolution.FAILED, CAPTION_MISSING_MSG)
                ),
                
                // checker for elements not identified by marker
                new ChildElementPresenceChecker(
                    HtmlElementStore.CAPTION_ELEMENT, 
                    // the child element is supposed to appear at least once
                    1,
                    // nmi when attribute is found
                    new ImmutablePair(TestSolution.NEED_MORE_INFO, CHECK_NATURE_OF_TABLE_WITH_CAPTION_MSG),
                    // nmi when attribute is not found
                    new ImmutablePair(TestSolution.NEED_MORE_INFO, CHECK_NATURE_OF_TABLE_WITHOUT_CAPTION_MSG)
                    )
            );
    }

}
