package diary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import diary.entity.Diary;
import diary.form.diary.GetForm;
import diary.form.diary.PostForm;
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
	@GetMapping("/form")
	public String formPage(Model model) {
		return "form";
	}

	/**
	 * 「一覧へ」選択時、一覧画面へ遷移
	 * 
	 * @param model
	 * @return resources/templates/list.html
	 */
	@PostMapping(path = { "/insert", "/form" }, params = "back")
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
}