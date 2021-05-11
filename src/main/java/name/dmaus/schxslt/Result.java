/*
 * Copyright 2019-2021 by David Maus <dmaus@dmaus.name>
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package name.dmaus.schxslt;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.ArrayList;

/**
 * Schematron validation result.
 */
public final class Result
{
    private static final String SVRL = "http://purl.oclc.org/dsdl/svrl";

    private final Document report;
    private final List<String> messages = new ArrayList<>();

    Result (final Document report)
    {
        this.report = report;

        readMessages(report.getElementsByTagNameNS(SVRL, "failed-assert"));
        readMessages(report.getElementsByTagNameNS(SVRL, "successful-report"));
    }

    /**
     * Return list of validation messages.
     *
     * @return Validation messages.
     */
    public List<String> getValidationMessages ()
    {
        return messages;
    }

    /**
     * Returns the validation report.
     *
     * @return Validation report
     */
    public Document getValidationReport ()
    {
        return report;
    }


    /**
     * Returns true if the validated document is valid.
     *
     * @return True if the validated document is valid
     */
    public boolean isValid ()
    {
        return messages.isEmpty();
    }

    private void readMessages (final NodeList nodes)
    {
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element)nodes.item(i);
            String message = String.format("%s %s %s", element.getLocalName(), element.getAttribute("location"), element.getTextContent());
            messages.add(message);
        }
    }
}
