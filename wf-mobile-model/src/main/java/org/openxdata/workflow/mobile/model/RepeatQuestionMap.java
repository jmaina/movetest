package org.openxdata.workflow.mobile.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;
import org.openxdata.db.util.Persistent;
import org.openxdata.db.util.PersistentHelper;

/**
 *
 * @author kay
 */
public class RepeatQuestionMap implements Persistent {

        private Vector questionMaps;

        public RepeatQuestionMap() {
        }

        public void setQuestionMaps(Vector questionMaps) {
                this.questionMaps = questionMaps;
        }

        public Vector getQuestionMaps() {
                return questionMaps;
        }

        public void clear() {
                if (questionMaps != null)
                        questionMaps.removeAllElements();
        }

        public String getQuestionValue(String questionName){
                if(questionMaps == null) return null;
                for (int i = 0; i < questionMaps.size(); i++) {
                        MQuestionMap mQuestionMap = (MQuestionMap)questionMaps.elementAt(i);
                        if(mQuestionMap.getQuestion().equals(questionName))
                                return mQuestionMap.getValue();
                }
                return null;
        }

        public synchronized void  add(MQuestionMap e) {
                if (questionMaps == null)
                        questionMaps = new Vector();
                 questionMaps.addElement(e);
        }

        public void write(DataOutputStream dos) throws IOException {
                PersistentHelper.write(questionMaps, dos);
        }

        public void read(DataInputStream dis) throws IOException, InstantiationException, IllegalAccessException {
                questionMaps = PersistentHelper.read(dis, new MQuestionMap().getClass());
        }
}
