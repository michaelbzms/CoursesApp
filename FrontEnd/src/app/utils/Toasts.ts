export class Toasts {

  static toast(msg: string, elemId?: string) {
    const toast = document.getElementById((elemId) ? elemId : 'toasts_div');
    if (!toast.classList.contains('show')) {
      toast.innerText = msg;
      toast.classList.add('show');
      setTimeout(() => {
        toast.classList.remove('show');
      }, 3000);  // must be 3000
    }
  }

}
