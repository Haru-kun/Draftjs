using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using BanhangMVC.Models;
using CaptchaMvc;
using CaptchaMvc.HtmlHelpers;

namespace BanhangMVC.Controllers
{
    public class DangkyController : Controller
    {
        QuanlyBanhangEntities db = new QuanlyBanhangEntities();


        [HttpGet]
        public ActionResult Dangky()
        {
            ViewBag.CauHoi = Cauhoi();
            return View();
        }

        [HttpPost]
        public ActionResult Dangky(ThanhVien tv,FormCollection f)
        {

            // kiem tra captcha hop le
            ViewBag.CauHoi = Cauhoi();
            if (this.IsCaptchaValid("Captcha is not valid"))
            {
                tv.MaLoaiTV = 1;
                db.ThanhViens.Add(tv);
                db.SaveChanges();
                return View();
            }
            ViewBag.Thongbao = "Mã sai. Xin nhập lại !!! ";
            return View();
        }

        [HttpGet]
        public ActionResult Dangky1()
        {
            ViewBag.CauHoi = ( Cauhoi());
            return View();
        }
        [HttpPost]

        public ActionResult Dangky1(ThanhVien tv)
        {
            ViewBag.CauHoi = Cauhoi();
            if (this.IsCaptchaValid("Captcha is not valid"))
            {
                if(ModelState.IsValid)
                {
                    ViewBag.Thongbao = "Them Thanh Cong !!!";
                    db.ThanhViens.Add(tv);
                    db.SaveChanges();
                }
                else
                {
                    ViewBag.Thongbao = "Them that bai !!!";
                }
                return View();
            }
            ViewBag.Thongbao = "Mã sai. Xin nhập lại !!! ";

            return View();
        }

        public List<String> Cauhoi()
        {
            List<String> list_cauhoi = new List<string>();
            list_cauhoi.Add("Tên con vật mà bận yêu thích ?");
            list_cauhoi.Add("Tên câu lạc bộ thể thao mà bạn yêu thích ?");
            list_cauhoi.Add("Tên ca sĩ mà bạn yêu thích ?");
            list_cauhoi.Add("Tên công việc mà bạn yêu thích ?");
            list_cauhoi.Add("Tên thú nuôi của bạn là gì ?");

            return list_cauhoi;
        }

        // Giải phóng bộ nhớ khi không sử dụng
        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                if (db != null)
                {
                    db.Dispose();
                }
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}