package com.example.paperpersonalizing.entity.papertreeComposite;

import com.example.paperpersonalizing.entity.PaperBo;

public class PaperAlerting extends Paper{
    /**
     * state为0表示未接收处理，1表示接收论文
     */
    private int state=0;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public PaperAlerting(PaperBo paperBo) {
        super(paperBo);
    }
}
