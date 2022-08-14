package hitpm_v2.ICES_beans_xml;

import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.in.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.io.*;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.info.*;

public class XML3 {
	
	XParser parser = new XesXmlParser();
	
	public FileInputStream convert(String filePathName) throws FileNotFoundException
	{
		return new FileInputStream(filePathName);
	}
	
	public XLog getLog(InputStream inputStream) throws Exception
	{
        Collection<XLog> logs;
        try {
            logs = parser.parse(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            logs = null;
        }
        if (logs == null) {
            // try any other parser
            for (XParser p : XParserRegistry.instance().getAvailable()) {
                if (p == parser) {
                    continue;
                }
                try {
                    logs = p.parse(inputStream);
                    if (logs.size() > 0) {
                        break;
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    logs = null;
                }
            }
        }

        // log sanity checks;
        // notify user if the log is awkward / does miss crucial information
        if (logs == null || logs.size() == 0) {
            throw new Exception("No processes contained in log!");
        }

        XLog log = logs.iterator().next();
        if (XConceptExtension.instance().extractName(log) == null) {
            XConceptExtension.instance().assignName(log, "Anonymous log imported from ");
        }

        if (log.isEmpty()) {
            throw new Exception("No process instances contained in log!");
        }

        return log;
	}
	
	public int getLogSize(XLog log)
	{
		return log.size();
	}
	
	public XLogInfo getLogInfo(XLog log)
	{
		return XLogInfoFactory.createLogInfo(log);
	}
	
}
