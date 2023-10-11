package com.realnet.formio.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.formio.Services.BoardService;
import com.realnet.formio.entity.Board;
import com.realnet.formio.entity.Boardmix;
import com.realnet.formio.entity.Card;
import com.realnet.formio.entity.Column;
import com.realnet.formio.entity.GetGoal;
import com.realnet.formio.entity.GetIteration;
import com.realnet.formio.entity.Getmilestone;
import com.realnet.formio.entity.Goal;
import com.realnet.formio.repository.BoardRepository;
import com.realnet.formio.repository.CardRepository;
import com.realnet.formio.repository.ColumnRepository;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
//@CrossOrigin("*")
@RequestMapping(value = "/api", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "/boards" })
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private ColumnRepository columnRepository;
	
	

//
//	@PostMapping("/create")
//	public ResponseEntity<?> add(@RequestBody Board board){
//		return ResponseEntity.ok(this.boardService.createBoard(board));
//	}
//	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody Board board) {
		return ResponseEntity.ok(this.boardService.updateBoard(board));
	}

	@GetMapping("/get-all")
	public List<Board> getAll() {
		return this.boardService.getAllBoards();
	}

	@GetMapping("/get-one/{bid}")
	public Board getBoardDetById(@PathVariable("bid") Long bid) {
		return this.boardService.getBoard(bid);
	}

	@DeleteMapping("/delete-board/{bid}")
	public void delete(@PathVariable("bid") Long bid) {
		this.boardService.deleteBoard(bid);
	}

	@PostMapping("/addBoardClmnsPrj")
	public ResponseEntity<?> addBoardColumnDetails(@RequestBody Board boardclms) {
		return ResponseEntity.ok(this.boardService.createBoardClmnsPrj(boardclms));
	}

	@PostMapping("/addBoardClmnsOther")
	public ResponseEntity<?> addBoardClmnsOtherDetails(@RequestBody Board boardclmsother) {
		return ResponseEntity.ok(this.boardService.createBoardClmnsOther(boardclmsother));
	}

//	@GetMapping("/get_goal")
//	public ArrayList<Object> goal() {
//		ArrayList<Object> list = new ArrayList<>();
//		Board bo = null;
//
//		List<Card> goal = cardRepository.findByGoal();
//		int vi = -1;
//		int vArray[] = new int[goal.size()];
//		for (int i = 0; i < goal.size(); i++) {
//			for (int j = i + 1; j < goal.size(); j++) {
//				Long bid = goal.get(i).getColumn().getBoard().getbId();
//				log.info("bid = ", bid);
//
//				Long bid1 = goal.get(j).getColumn().getBoard().getbId();
//				log.info("bid1 =", bid1);
//
//				if (bid == bid1) {
//					vArray[j] = vi;
//				}
//			}
//		}
//		for (int i = 0; i < goal.size(); i++) {
//			if (vArray[i] != vi) {
//				log.info("goal is ", goal.get(i).getColumn().getBoard().getbId());
//				System.out.println(goal.get(i));
//
//				Long bid = goal.get(i).getColumn().getBoard().getbId();
//
//				bo = boardRepository.findById(bid).orElseThrow(null);
//				List<Card> cards = cardRepository.findByGoal();
//				for (Card card : cards) {
//
//					Long cId = card.getColumn().getcId();
//
//					List<Column> cols = columnRepository.findAllById(bo.getbId());// find all column by board id
//
//					for (Column col : cols) {
//						if (cId.equals(col.getcId())) {
//							List<Card> ca2 = cardRepository.findgoalbycid(cId);// find card by card cid
//							col.setCards(ca2);
//
//						}
//
//					}
//					bo.setColumns(cols);
//
//				}
//				list.add(bo);
//			}
//		}
//
//		return list;
//	}

	//GET GOAL
//	@GetMapping("/get_bygoal")
//	public List<Board> getgoal() {
//		List<Board> list1 = new ArrayList<Board>();
//
//		List<Card> cards = cardRepository.findGoal();
//		for (int i = 0; i < cards.size(); i++) {
//			Board bo1 = boardRepository.findById(cards.get(i).getColumn().getBoard().getbId()).orElseThrow(null);
//			Board bo = new Board();
//			bo.setbId(bo1.getbId());
//			bo.setbName(bo1.getbName());
//			bo.setProject_id(bo1.getProject_id());
//			bo.setType(bo1.getType());
//			Long cid = cards.get(i).getColumn().getcId();
//			Column col = columnRepository.findById(cid).orElseThrow(null);
//			col.setCard(cards.get(i));
//			Long CardId = cards.get(i).getId();
////			Card gl = cardRepository.findById(CardId).orElseThrow(null);
////			Goal goal2 = new Goal();
////			goal2.setGoal(gl.getGoal());
//			
//			GetGoal goal = cardRepository.findforgoal(CardId);
//			
//			col.setCategory(goal);
//			bo.setCols(col);
//			bo.setCols(col);
//
//			list1.add(bo);
//
//		}
//
//		return list1;
//	}
	
	//GET MILESTONE
//	@GetMapping("/get_bymilestone")
//	public List<Board> getmilestone() {
//		List<Board> list1 = new ArrayList<Board>();
//
//		List<Card> cards = cardRepository.findAll();
//		for (int i = 0; i < cards.size(); i++) {
//			Board bo1 = boardRepository.findById(cards.get(i).getColumn().getBoard().getbId()).orElseThrow(null);
//			Board bo = new Board();
//			bo.setbId(bo1.getbId());
//			bo.setbName(bo1.getbName());
//			bo.setProject_id(bo1.getProject_id());
//			bo.setType(bo1.getType());
//			Long cid = cards.get(i).getColumn().getcId();
//			Column col = columnRepository.findById(cid).orElseThrow(null);
//			col.setCard(cards.get(i));
//			Long CardId = cards.get(i).getId();
//			Getmilestone goal = cardRepository.findMilestone(CardId);
//			col.setCategory1(goal);
////			bo.setCols(col);
//			bo.setCols(col);
//
//			list1.add(bo);
//
//		}
//
//		return list1;
//	}
//	
//	//GET ITERATION
//	@GetMapping("/get_iteration")
//	public List<Board> getiteration() {
//		List<Board> list1 = new ArrayList<Board>();
//
//		List<Card> cards = cardRepository.findAll();
//		for (int i = 0; i < cards.size(); i++) {
//			Board bo1 = boardRepository.findById(cards.get(i).getColumn().getBoard().getbId()).orElseThrow(null);
//			Board bo = new Board();
//			bo.setbId(bo1.getbId());
//			bo.setbName(bo1.getbName());
//			bo.setProject_id(bo1.getProject_id());
//			bo.setType(bo1.getType());
//			Long cid = cards.get(i).getColumn().getcId();
//			Column col = columnRepository.findById(cid).orElseThrow(null);
//			col.setCard(cards.get(i));
//			Long CardId = cards.get(i).getId();
//			GetIteration goal = cardRepository.finditeration(CardId);
//			col.setCategory2(goal);
////			bo.setCols(col);
//			bo.setCols(col);
//
//			list1.add(bo);
//
//		}
//
//		return list1;
//	}
//	@GetMapping("/gettest/{id}")
//	public GetGoal get1(@PathVariable Long id){
////		long cardidLong =  180 ;
//		GetGoal goal = cardRepository.findforgoal(id);
//		return goal;
//		
//	}

//	@GetMapping("/get_bygoal")
//	public List<Boardmix> get() {
//		List<Boardmix> list = boardRepository.findByGoal();
//		return list;
//
//	}
//
}
