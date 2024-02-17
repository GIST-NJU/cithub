// repository的一些公用的函数
import pinia from '../store/store'
import { usePaperInfoStore } from '../store/paperinfoStore'
import { useAuthorStore } from '../store/authorStore'
import { useInstitutionStore } from '../store/institutionStore'
import { useVenueStore } from '../store/venueStore'
import { useUserStore } from '../store/userStore';
import { useModuleStore } from '../store/module';
import { request } from '../request';
const userStore = useUserStore(pinia)
const moduleStore = useModuleStore(pinia)
const PaperInfoStore = usePaperInfoStore(pinia)
const AuthorStore = useAuthorStore(pinia)
const InstitutionStore = useInstitutionStore(pinia)
const VenueStore = useVenueStore(pinia)

const listAllPapers = (paginationObj) => {
    // 获取所有文章的数据
    request({
        url: '/repo/list/listAllpapers',
        method: 'POST',
        data: paginationObj
    }).then((res) => {
        // console.log("listAllPapers",res)
        PaperInfoStore.paperinfos.length = 0
        PaperInfoStore.searchKeyWords = ''
        PaperInfoStore.paperinfos.push(...res.listEntityPage.records)

        paginationObj.total = res.listEntityPage.total
        // console.log("paginationObj.total",paginationObj.total)

        PaperInfoStore.total = paginationObj.total

        // console.log("paginationObj.total",paginationObj.total)

    }).catch((error) => {
        console.log("错误是", error)
    })
}

const listAllScholars = () => {

    request(
        {
            url: 'repo/author/listallauthor',
            method: 'POST',
            data: {
                obj: "Combinatorial Testing"
            }


        }
    ).then((res) => {
        // console.log("authors是")
        // console.log("收到的authors", res)
        AuthorStore.authorsArray.length = 0
        for (let i = 0, len = res.authors.length; i < len; i++) {
            // authors.push(res.authors[i].authorname)
            AuthorStore.authorsArray.push(res.authors[i].authorname)
        }
        // authors = authors.sort()
        AuthorStore.authorsArray = AuthorStore.authorsArray.sort()

    }).catch((error) => { console.log(error) })

}

const listAllInstitutions = () => {

    request(
        {
            url: 'repo/author/listAllInstitutions',
            method: 'POST',
            data: {
                obj: "Combinatorial Testing"
            }


        }
    ).then((res) => {
        // console.log("收到的Institutions", res)
        InstitutionStore.InstitutionArray.length = 0
        // console.log("res.Institutions.length",res.Institutions.length)
        let len = res.Institutions.length
        for (let i = 0; i < len; i++) {
            // console.log("res.Institutions[i].institution",res.Institutions[i].institution)
            InstitutionStore.InstitutionArray.push(res.Institutions[i].institution)
        }
        // console.log( "  InstitutionStore.InstitutionArray", InstitutionStore.InstitutionArray)
        // authors = authors.sort()
        InstitutionStore.InstitutionArray = InstitutionStore.InstitutionArray.sort()

    }).catch((error) => { console.log(error) })

}

const listallVenue = () => {

    request(
        {
            url: 'repo/list/listallVenue',
            method: 'POST',
            data: {
                obj: "Combinatorial Testing"
            }


        }
    ).then((res) => {

        let tempArray = []
        for (let i = 0, len = res.res.length; i < len; i++) {
            if (res.res[i].abbr == 'Phd' && res.res[i].booktitle.indexOf('not found') == -1) VenueStore.VenueArrayPhd.push(res.res[i].booktitle)
            else if (res.res[i].abbr == 'Book' && res.res[i].booktitle.indexOf('not found') == -1) VenueStore.VenueArrayBook.push(res.res[i].booktitle)
            else if (res.res[i].abbr == 'other' && res.res[i].booktitle.indexOf('not found') == -1) VenueStore.VenueArrayOther.push(res.res[i].booktitle)
            else tempArray.push(res.res[i].abbr)

        }

        tempArray = Array.from(new Set(tempArray))
        for (var i = 0; i < tempArray.length; i++) {
            VenueStore.VenueArray.push(tempArray[i])
        }
        // console.log("VenueStore.VenueArray",VenueStore.VenueArray)
        // console.log("VenueStore.VenueArrayPhd",VenueStore.VenueArrayPhd)
        // console.log("VenueStore.VenueArrayBook",VenueStore.VenueArrayBook)
        // console.log("VenueStore.VenueArrayOther",VenueStore.VenueArrayOther)
        VenueStore.VenueArray = VenueStore.VenueArray.sort()
        VenueStore.VenueArrayPhd = VenueStore.VenueArrayPhd.sort().reverse()
        VenueStore.VenueArrayBook = VenueStore.VenueArrayBook.sort().reverse()
        VenueStore.VenueArrayOther = VenueStore.VenueArrayOther.sort().reverse()
    }).catch((error) => { console.log(error) })

}

export {listAllPapers,listAllScholars,listAllInstitutions,listallVenue}