import {Status} from "@/store/catering/services/Status";

export function createCateringItemAdapter(type) {
    return {
        label: '',
        description: '',
        type: type || {},
        listId: 0,
        quantity: '1',
        status: Status[0]
    }
}
