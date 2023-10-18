package com.realnet.formio.Services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.ProjectManagement.Entity.Pm_Goal;
import com.realnet.ProjectManagement.Repository.GoalRepository;
import com.realnet.formio.Services.BoardService;
import com.realnet.formio.Services.ColumnService;
import com.realnet.formio.entity.Board;
import com.realnet.formio.entity.Card;
import com.realnet.formio.entity.Column;
import com.realnet.formio.repository.BoardRepository;
import com.realnet.formio.repository.CardRepository;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private GoalRepository goalRepository;
	@Autowired
	private CardRepository cardRepository;
	
    
    @Autowired
    private ColumnService colService;
	@Override
	public Board createBoard(Board board) {
		return this.boardRepository.save(board);
	}

	@Override
	public Board updateBoard(Board board) {
		return this.boardRepository.save(board);
	}

	@Override
	public Board getBoard(Long id) {
		return this.boardRepository.findById(id).get();
	}

	@Override
	public List<Board> getAllBoards() {
		return this.boardRepository.findAll();
	}

	@Override
	public void deleteBoard(Long id) {
		this.boardRepository.deleteById(id);
	}
	@Override
    public Board createBoardClmnsPrj(Board boardclms) {
        // TODO Auto-generated method stub
            Board savedboard= this.boardRepository.save(boardclms);
            //List<Column> columns=new ArrayList <Column>();
            List<Column> columns=new ArrayList();
              
            Column newboard= new Column();
            newboard.setcName("Backlog");
            newboard.setBoard(savedboard);
            columns.add (colService.createColumn(newboard));
            
            Column newboard1= new Column();
            newboard1.setcName("Unscheduled");
            newboard1.setBoard(savedboard);
            columns.add (colService.createColumn(newboard1));
            
            Column newboard2= new Column();
            newboard2.setcName("Current Sprint");
            newboard2.setBoard(savedboard);
            columns.add (colService.createColumn(newboard2));
            
            Column newboard3= new Column();
            newboard3.setcName("Under Development");
            newboard3.setBoard(savedboard);
            columns.add (colService.createColumn(newboard3));
            
            Column newboard4= new Column();
            newboard4.setcName("Review Pending");
            newboard4.setBoard(savedboard);
            columns.add (colService.createColumn(newboard4));
            
            Column newboard5= new Column();
            newboard5.setcName("Ready for Deployment");
            newboard5.setBoard(savedboard);
            columns.add (colService.createColumn(newboard5));
            
            Column newboard6= new Column();
            newboard6.setcName("Completed");
            newboard6.setBoard(savedboard);
            columns.add (colService.createColumn(newboard6));
            
            savedboard.setColumns(columns);
            return this.boardRepository.save(savedboard);
    
    }
    @Override
    public Board createBoardClmnsOther(Board boardclmsother) {
        // TODO Auto-generated method stub
        Board savedboard= this.boardRepository.save(boardclmsother);
        List<Column> columns=new ArrayList <Column>();
        //List<Column> columns=new ArrayList();
          
        Column newboard= new Column();
        newboard.setcName("ToDo");
        newboard.setBoard(savedboard);
        columns.add (colService.createColumn(newboard));
        
        Column newboard1= new Column();
        newboard1.setcName("Working");
        newboard1.setBoard(savedboard);
        columns.add (colService.createColumn(newboard1));
        
        Column newboard2= new Column();
        newboard2.setcName("Completed");
        newboard2.setBoard(savedboard);
        columns.add (colService.createColumn(newboard2));
        
        savedboard.setColumns(columns);
        return this.boardRepository.save(savedboard);
        }

	
	public Board getgoal(Long bId) {
		Board bo = boardRepository.findById(bId).orElseThrow(null);		
		List<Column> columns = bo.getColumns();
		for (Column col : columns) {
		
				List<Pm_Goal> goal = goalRepository.findByColumn_id(col.getcId());
				for(Pm_Goal pm :goal) {
					List<Card> cards = cardRepository.findByGoal(pm.getName(),col.getcId());
					pm.setCards(cards);
					}
				col.setCategory(goal);		
		}
		bo.setCols(columns);
		return bo;
	}

}
