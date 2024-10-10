package com.traffic.Service;

import com.traffic.Mapper.FetchData;
import com.traffic.pojo.Position;
import org.springframework.stereotype.Service;

@Service
public class PositionService {
    private FetchData fetchData;
    private Position[] positionResult;
    private  Position pos1;
    private  Position pos2;

    public Position[] getPosition() {

        pos1=new Position(new double[]{104.430113, 30.477069},new double[]{104.21486, 30.75641});
        pos2=new Position(new double[]{104.274964, 30.74687},new double[]{103.807823, 30.86728});
        positionResult= new Position[]{pos1,pos2};
        return positionResult;

    }
}
