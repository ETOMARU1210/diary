package diary.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import diary.entity.Diary;
import diary.form.diary.GetForm;
import diary.form.diary.PostForm;
import diary.form.diary.PutForm;
import diary.service.DiaryService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/diary")
public class DiaryController {

	private final DiaryService diaryservice;

	@Autowired
	public DiaryController(DiaryService diaryservice) {
		this.diaryservice = diaryservice;
	}

	/**
	 * 日記アプリの一覧画面を表示
	 * 
	 * @param model
	 * @return resources/templates/list.html
	 */
	@GetMapping
	public String diaryList(@ModelAttribute GetForm form, Model model) {
		List<Diary> list = diaryservice.findList(form);
		model.addAttribute("list", list);
		model.addAttribute("getForm", form);
		return "list";
	}

	/**
	 * 新規登録へ遷移
	 * 
	 * @param model
	 * @return resources/templates/form.html
	 */
	@PostMapping("/form")
	public String formPage(@ModelAttribute PutForm form, Model model) {
		model.addAttribute("putForm", form);
		if (form.getUpdateFlag()) {
			model.addAttribute("update", true);
		} else {
			model.addAttribute("update", false);
		}
		return "form";
	}

	/**
	 * 「一覧へ」選択時、一覧画面へ遷移
	 * 
	 * @param model
	 * @return resources/templates/list.html
	 */
	@PostMapping(path = { "/insert", "/form", "/update" }, params = "back")
	public String backPage(Model model) {
		return "redirect:/diary";
	}

	/**
	 * 日記を新規登録
	 * 
	 * @param postForm
	 * @param model
	 * @return
	 */
	@PostMapping(path = "/insert", params = "insert")
	public String insert(@Valid @ModelAttribute PostForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("error", "パラメータエラーが発生しました。");
			return "form";
		}
		int count = diaryservice.insert(form);
		model.addAttribute("postForm", form);
		return "redirect:/diary";
	}
	
	/**
	 * 一件タスクデータを取得し、詳細ページ表示
	 * @param id
	 * @param model
	 * @return resources/templates/detail.html
	 */
	@GetMapping("/{id}")
	public String showUpdate(
			@PathVariable int id,
			Model model
			) {
		Optional<Diary> diaryOpl = Optional.ofNullable(diaryservice.findById(id));
		
		if(diaryOpl.isPresent()) {
			model.addAttribute("diary", diaryOpl.get());
			return "detail";
		} else {
			model.addAttribute("error", "対象データが存在しません");
			return "detail";
		}
	}
	/**
	  * 日記を編集
	  * @param putForm
	  * @param model
	  * @return
	  */
	@PostMapping(path="/update", params="update")
	public String update(@ModelAttribute PutForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("error", "パラメータエラーが発生しました。");
			return "form";
		}
		int count = diaryservice.update(form);
		return "redirect:/diary";
	}
	
	/**
	 * 日記を削除
	 * @param id
	 * @param model
	 * @return
	 */
	@PostMapping("/delete")
	public String delete(
			@RequestParam int id,
			Model model
			) {
		int count = diaryservice.delete(id);
		return "redirect:/diary";
	}
}